import matplotlib as mpl
import numpy as np
import matplotlib.pyplot as plt


def plotAlphaGamma():
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

def plotEpsilonQinit():
    epsilons = [0.1]
    qinits= [0, 5, 10, 10000]

    # for gamma in gammas:
    for epsilon in epsilons:
        for qinit in qinits:
            data = np.genfromtxt('../../../qlearnepsilon'+ str(epsilon) + ' '+
                    str(qinit)+ '.csv', delimiter=',', names=['x', 'y'])

            plt.plot(data['x'], data['y'], label='eps = '+str(epsilon) +
            'qinit='+str(qinit))

    plt.legend()
    plt.xlim(0,1000)
    plt.show()

plotEpsilonQinit()


