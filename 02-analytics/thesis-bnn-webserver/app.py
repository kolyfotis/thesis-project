import decimal
import pandas as pd
from flask import Flask
from flask import request
import mysql.connector

app = Flask(__name__)


def assign_values_to_features(user_df, cars_df):
    # create the columns for the df with car features probabilities
    car_features_columns = [
        'id',
        'door_number_3',
        'door_number_5',
        'body_type_Coupe',
        'body_type_Sedan',
        'body_type_SUV',
        'body_type_Compact',
        'gearbox_Automatic',
        'gearbox_Manual',
        'fuel_type_Gasoline',
        'fuel_type_Diesel',
        'fuel_type_Electric',
        'make_Audi',
        'make_BMW',
        'make_Ford',
        'make_Mercedes',
        'make_Opel',
        'make_Volkswagen'
    ]

    # Temp list to store the dictionaries
    lst = []

    # iterate over cars_df rows
    for index, car in cars_df.iterrows():
        # init 0.0 dictionary with all feature columns
        temp_dict = {cfv: 0.0 for cfv in car_features_columns}
        temp_dict['id'] = car['id']

        # iterate user_df rows
        for idx, ud in user_df.iterrows():
            # print(f"{ud['field_name']}_{ud['field_value']}")

            # if a car has the feature
            if str(car[ud['field_name']]) == ud['field_value']:
                # update the dict value for key
                temp_dict[f"{ud['field_name']}_{ud['field_value']}"] = float(ud['time'])

        # Create a List and add it to the car_features_values DF
        lst.append(temp_dict)

    # return N x M dataframe where
    # N: len(cars_df), M: len(cars_features_time_cols)
    return pd.DataFrame(lst, columns=car_features_columns)


@app.route('/')
def hello_world():  # put application's code here
    return 'Hello World!'


@app.get('/evaluateRecommendationsForUser')
def evaluate_recommendations_for_user():
    # Setup DB connection for python env in WSL
    conn = mysql.connector.connect(
        user='wsl_root', password='password',
        host='172.30.176.1', database='thesis'
    )
    # Setup DB connection for python env in System
    # conn = mysql.connector.connect(
    #     user='user1', password='password',
    #     host='localhost', database='thesis'
    # )
    cursor = conn.cursor()

    # fetch user_data as pandas dataFrame
    username = request.args.get('username')
    select_user_data_query = (
        "SELECT username, field_name, field_value, normalized_time_spent "
          "FROM user_data "
          "WHERE username = %s;")
    cursor.execute(select_user_data_query, [username])
    user_cols = ['username', 'field_name', 'field_value', 'time']
    user_data = pd.DataFrame(cursor.fetchall(), columns=user_cols)
    # print(user_data, '\n', 55*'-')

    # fetch cars as pandas dataFrame
    select_cars_query = (
        "SELECT id, door_number, body_type, gearbox, fuel_type, make "
         "from cars;")
    cursor.execute(select_cars_query)
    cars_cols = ['id', 'door_number', 'body_type', 'gearbox',
                 'fuel_type', 'make']
    cars = pd.DataFrame(cursor.fetchall(), columns=cars_cols)
    # print(cars, '\n', 55*'-')

    car_feat_values = assign_values_to_features(user_data, cars)
    # print(car_feat_values, '\n', 55*'-')

    # Evaluate the relevance for each car, and add it
    # as new column to the car_feat_values. This is the shape
    # for the model
    cars['relevance'] = (
            car_feat_values.drop('id', axis=1).sum(axis=1) / 5)

    # Concat username, car_id & relevance
    u_name = pd.DataFrame(
        [user_data['username'][0]] * len(cars.index),
        columns=['username']
    )
    # Convert relevance from float64 to Decimal(10,9)
    relevance = (cars['relevance']
                  .apply(lambda x: decimal.Decimal(x))
                  .apply(lambda x: x.quantize(decimal.Decimal('0.000000001')))
    )
    recommendations = pd.concat(
        [u_name, cars[['id']], relevance], axis=1)

    # SQL query to insert/update user_recommendations
    save_recommendations_query = (
        "INSERT INTO "
        "user_recommendations(username, car_id, relevance) "
        "VALUES (%(u_name)s, %(c_id)s, %(rel)s) "
        "ON DUPLICATE KEY UPDATE relevance = %(rel)s;")

    # Manually iterate and insert/update each row
    # of user_recommendations
    for index, rec in recommendations.iterrows():
        cursor.execute(
            save_recommendations_query,
            {'u_name': rec['username'],
             'c_id': rec['id'],
             'rel': rec['relevance']}
        )

    conn.commit()
    conn.close()

    # Return the list of cars sorted by probability
    return recommendations.sort_values(
        ['relevance'], ascending=False).values.tolist()

if __name__ == '__main__':
    app.run()
