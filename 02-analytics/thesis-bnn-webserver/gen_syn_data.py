import random
import pandas as pd

car_features_columns = [
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

N_SAMPLES = 10_000

def gen_mut_excl(m_cols):
    """
    Generates a list with col number of mutually exclusive
    columns. For each row one column, randomly selected,
    will have a value 0.01 - 1.0, and the others will be 0.0.
    :param:
        m_cols (int): The number of columns
    :return:
        data (list): A list with the results
    """
    data = []

    for _ in range(N_SAMPLES):
        column_values = [0.0] * m_cols
        random_index = random.randint(0, m_cols - 1)
        random_value = random.uniform(0.01, 1.0)
        column_values[random_index] = round(random_value, 9)
        data.append(column_values)

    return data

door_columns = [
    x for x in car_features_columns if x.startswith('door_number')]
door_number = pd.DataFrame(gen_mut_excl(len(door_columns)),
                           columns=[door_columns])
print('Door number: ', door_number.shape)

body_columns = [
    x for x in car_features_columns if x.startswith('body_type')]
body_type = pd.DataFrame(gen_mut_excl(len(body_columns)),
                         columns=[body_columns])
print('Body Type', body_type.shape)

gearbox_columns = [
    x for x in car_features_columns if x.startswith('gearbox')]
gearbox = pd.DataFrame(gen_mut_excl(len(gearbox_columns)),
                       columns=[gearbox_columns])
print('Gearbox',gearbox.shape)

fuel_type_columns = [
    x for x in car_features_columns if x.startswith('fuel_type')]
fuel_type = pd.DataFrame(gen_mut_excl(len(fuel_type_columns)),
                         columns=[fuel_type_columns])
print('Fuel Type', fuel_type.shape)

make_columns = [
    x for x in car_features_columns if x.startswith('make')]
make = pd.DataFrame(
    gen_mut_excl(len(make_columns)), columns=[make_columns])
print('Make: ', make.shape)

# Concatenate columns and evaluate relevance for each car
cars = pd.concat(
    [door_number, body_type, gearbox, fuel_type, make], axis=1)

cars['relevance'] = round(cars.sum(axis=1) / 5, 9)
print('Cars: ', cars.shape)

cars.reset_index(inplace=True)
cars.index.name = 'id'

print(cars.info())
cars.to_csv('synthetic_data.csv', index=False)
