import matplotlib as mpl
import numpy as np
import matplotlib.pyplot as plt


def plotTest():
    for i in xrange(2,3):
        data = np.genfromtxt('../../../smarttest'+str(i)+'.csv', delimiter=',', names=['x', 'y',
        'z'])
        plt.plot(data['x'], data['y'], label='vs. smart prey')
        data = np.genfromtxt('../../../test'+str(i)+'.csv', delimiter=',',
                names=['x', 'y', 'z'])
        plt.plot(data['x'], data['y'], label='vs. random prey')
    plt.title("Predator winrate of 2 predators")
    plt.legend()
    plt.show()

def plotPredatorWinrates():
    for i in xrange(1,3):
        data = np.genfromtxt('../../../smarttest'+str(i)+'.csv', delimiter=',',
                names=['x', 'y', 'z'])
        plt.plot(data['x'], data['y'], label=str(i) + ' predators')
    plt.title("Predator winrates")
    plt.legend()
    plt.show()

def plotAlphaGamma():
    alphas = [0.1, 0.2, 0.3, 0.4, 0.5]
    gammas = [0.1, 0.5, 0.7, 0.9]

    # for gamma in gammas:
    gamma = gammas[1]
    for alpha in alphas:
        data = np.genfromtxt('../../../qlearn'+ str(alpha) + ' '+ str(gamma)+
                '.csv', delimiter=',', names=['x', 'y'])

        plt.plot(data['x'], data['y'], label='alpha = '+str(alpha))

    plt.legend()
    plt.show()
    
def plotSarsa():
    alphas = [0.1, 0.2, 0.3, 0.4, 0.5]
    gammas = [0.1, 0.5, 0.7, 0.9]

    # for gamma in gammas:
    for gamma in gammas:
        for alpha in alphas:
            data = np.genfromtxt('../../../sarsa'+ str(alpha) + ' '+ str(gamma)+
                    '.csv', delimiter=',', names=['x', 'y'])
    
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

def plotQLearnCompareEpsilonGreedySoftmax():
    policies = ["", "softmax"]

    gamma = 0.1
    alpha = 0.1
    for policyId in policies:
        data = np.genfromtxt('../../../qlearn'+ str(alpha) + ' '+ str(gamma)+ policyId +
                '.csv', delimiter=',', names=['x', 'y'])

        if policyId == "":
			# Make up for e-greedy defaults to no policy id
            policyId = "e-greedy"
			
        plt.plot(data['x'], data['y'], label=policyId)

    plt.legend()
    plt.show()
    
def plotMonteCarloCompareEpsilonGreedySoftmax():
    policies = ["", "softmax"]

    gamma = 0.1
    alpha = 0.1
    for policyId in policies:
        data = np.genfromtxt('../../../montecarlo'+  policyId +
                '.csv', delimiter=',', names=['x', 'y'])

        if policyId == "":
            # Make up for e-greedy defaults to no policy id
            policyId = "e-greedy"
            
        plt.plot(data['x'], data['y'], label=policyId)

    plt.legend()
    plt.show()

# plotTest()
plotPredatorWinrates()
#plotQLearnCompareEpsilonGreedySoftmax()
#plotAlphaGamma()
# plotSarsa()
#plotMonteCarloCompareEpsilonGreedySoftmax()

