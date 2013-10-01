import matplotlib as mpl
import numpy as np
import matplotlib.pyplot as plt

alphas = [0.1, 0.2, 0.3, 0.4, 0.5]
gammas = [0.1, 0.5, 0.7, 0.9]

# for gamma in gammas:
gamma = gammas[0]
for alpha in alphas:
    data = np.genfromtxt('../../../qlearn'+ str(alpha) + ' '+ str(gamma)+ '.csv', delimiter=',', skip_header=10,
                     skip_footer=10, names=['x', 'y'])

    plt.plot(data['x'], data['y'], label='alpha = '+str(alpha))

plt.legend()
plt.show()

