import keras.saving
import numpy as np
import pandas as pd
import tensorflow as tf
import tensorflow_probability as tfp
import matplotlib.pyplot as plt

from sklearn.model_selection import train_test_split
from tensorflow.keras import Sequential
from sklearn.preprocessing import StandardScaler

seed = 213
np.random.seed(seed)
tf.random.set_seed(seed)
dtype = tf.float32

@keras.saving.register_keras_serializable()
class CustomDenseVariational(tfp.layers.DenseVariational):
    def __init__(self, **kwargs):
        # super(CustomDenseVariational, self).__init__(**kwargs)
        super().__init__(**kwargs)

    def get_config(self):
        config = super().get_config().copy()
        config.update({
            'make_prior_fn': self._make_prior_fn,
            'make_posterior_fn': self._make_posterior_fn,
            'units': self.units,
            'activation': self.activation,
            'name': self._name
        })
        return config

    @classmethod
    def from_config(cls, config):
        config['make_prior_fn'] = keras.layers.deserialize(config['make_prior_fn'])
        config['make_posterior_fn'] = keras.layers.deserialize(config['_make_posterior_fn'])
        config['units'] = keras.layers.deserialize(config['units'])
        config['activation'] = keras.layers.deserialize(config['activation'])
        config['name'] = keras.layers.deserialize(config['_name'])
        return cls(**config)


def prior(kernel_size, bias_size, dtype=None):
    n = kernel_size + bias_size
    prior_model = tf.keras.Sequential([
        tfp.layers.VariableLayer(2 * n, dtype=dtype),
        tfp.layers.DistributionLambda(
            lambda t: tfp.distributions.Independent(
                tfp.distributions.Normal(loc=t[..., :n], scale=0.01 + tf.math.softplus(t[..., n:])),
                reinterpreted_batch_ndims=1
            )
        )
    ])
    return prior_model


def posterior(kernel_size, bias_size, dtype=None):
    n = kernel_size + bias_size
    posterior_model = tf.keras.Sequential([
        tfp.layers.VariableLayer(2 * n, dtype=dtype),
        tfp.layers.DistributionLambda(
            lambda t: tfp.distributions.Independent(
                tfp.distributions.Normal(loc=t[..., :n], scale=0.01 + tf.math.softplus(t[..., n:])),
                reinterpreted_batch_ndims=1
            )
        )
    ])
    return posterior_model


def build_bayesian_model():
    model = Sequential([
        CustomDenseVariational(
            units=1,
            make_prior_fn=prior,
            make_posterior_fn=posterior,
            activation='sigmoid',
            name='layer_0'
        ),
        CustomDenseVariational(
            units=17,
            make_prior_fn=prior,
            make_posterior_fn=posterior,
            activation='sigmoid',
            name='layer_1'
        ),
        CustomDenseVariational(
            units=9,
            make_prior_fn=prior,
            make_posterior_fn=posterior,
            activation='sigmoid',
            name='layer_2'
        ),
        CustomDenseVariational(
            units=1,
            make_prior_fn=prior,
            make_posterior_fn=posterior,
            activation='sigmoid',
            name='layer_3'
        )
    ])
    return model

# load data
data = pd.read_csv('synthetic_data.csv')

X = data.drop(['relevance', 'index'], axis=1)
y = data['relevance']

# divide into train and test splits
X_train, X_test, y_train, y_test = train_test_split(
    X, y, test_size=0.2)

# Define the Bayesian model
model = build_bayesian_model()

# Compile the Bayesian model
model.compile(
    optimizer=tf.keras.optimizers.Adam(),
    loss=tf.keras.losses.MeanSquaredError(),
    metrics=[tf.keras.metrics.RootMeanSquaredError()]
)

# Scale inputs
scaler = StandardScaler()
X_train = scaler.fit_transform(X_train)
X_test = scaler.transform(X_test)

history = model.fit(
    X_train, y_train,
    epochs=1000,
    validation_data=(X_test, y_test)
)

tf.keras.models.save_model(
    model, 'bayesian_nn.keras', overwrite=True)

losses = pd.DataFrame(history.history)

losses[['root_mean_squared_error',
        'val_root_mean_squared_error']].plot()

y_pred = model.predict(X_test)

plt.tight_layout()
plt.show()
