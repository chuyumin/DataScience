import zipfile
import os
import random
import numpy as np

from sklearn.neighbors import KNeighborsClassifier
from sklearn import model_selection
import joblib

# load data
def generatewords() :
    words = set(("a"));
    words.remove("a");
    file_paths = ["D:/线性表" , "D:/字符串" , "D:/数组" , "D:/查找算法", "D:/排序算法" , "D:/数字操作" , "D:/树结构" , "D:/图结构"]
    for file_path in file_paths:
        word = generateword(file_path)
        for w in word:
            words.add(w)
    print(len(words))
    # result_list = random_100(list(words) , 100);
    return list(words)

def generateword(file_path):
    word = set(("a"));
    word.remove("a");
    zip_names = os.listdir(file_path)
    for label in range(0, len(zip_names)):
        print(zip_names[label])
        with zipfile.ZipFile(file_path + "/" + zip_names[label], "r") as zip_file:
            with zip_file.open(".mooctest/answer.py") as content:
                for line in content:
                    str_line = str(line)
                    str_line = str_line.replace("\\t", " ")
                    str_line = str_line.replace("\\n", " ")
                    temp_list = str_line[2:len(str_line) - 3].split(" ")
                    for index in range(0, len(temp_list)):
                        if temp_list[index] == "":
                            pass
                        else:
                            word.add(temp_list[index])
    return list(word)

def generatevectors():
    words = generatewords()
    vector_list = np.empty(shape=[0,6309],dtype=int)
    label_list = np.empty(shape=[0,1],dtype=str)
    file_paths = ["D:/线性表", "D:/字符串", "D:/数组", "D:/查找算法", "D:/排序算法", "D:/数字操作", "D:/树结构", "D:/图结构"]
    for file_path in file_paths:
        vertor = generatevector(file_path , words)
        vector_list = np.append(vector_list ,  vertor , axis=0)
        size = np.shape(vertor)[0]
        feature = [];
        for i in range(0 , size):
            feature.append(file_path[3:])
        feature = np.array(feature)
        feature = feature.reshape(size , 1)
        label_list = np.append(label_list , feature , axis=0)
    X_Train, X_Test, Y_Train, Y_Test = model_selection.train_test_split(vector_list, label_list, test_size=0.1, random_state=0)
    clf = KNeighborsClassifier(n_neighbors=8)
    clf.fit(X_Train, Y_Train)
    joblib.dump(clf, 'target.pkl')
    clf = joblib.load('target.pkl')
    print(clf.predict(X_Test))
    print(clf.score(X_Test, Y_Test))


def generatevector(file_path, words):
    list = np.empty(shape=[0,6309],dtype=int)
    zip_names = os.listdir(file_path)
    for label in range(0, len(zip_names)):
        # print(zip_names[label])
        with zipfile.ZipFile(file_path + "/" + zip_names[label], "r") as zip_file:
            with zip_file.open(".mooctest/answer.py") as content:
                temp_vector = [0 for i in range(0 , 6309)]
                for line in content:
                    str_line = str(line)
                    str_line = str_line.replace("\\t", " ")
                    str_line = str_line.replace("\\n", " ")
                    temp_list = str_line[2:len(str_line) - 3].split(" ")
                    for index in range(0, len(temp_list)):
                        if temp_list[index] in words:
                            temp_vector[words.index(temp_list[index])] += 1
            temp_vector = np.array(temp_vector)
            temp_vector = temp_vector.reshape(1 ,6309)
            list = np.append(list , temp_vector , axis=0)
    # print(words)
    print(list.shape)
    # feature = [];
    # for i in range(0 , 31):
    #     feature.append("i")
    # feature = np.array(feature)
    # feature = feature.reshape(31 , 1)
    # print(feature)
    # print(feature.shape)
    # X_Train,X_Test,Y_Train,Y_Test = model_selection.train_test_split(list, feature, test_size=0.6, random_state=0)
    # clf = KNeighborsClassifier(n_neighbors=3)
    # clf.fit(X_Train, Y_Train)
    # joblib.dump(clf , 'target.pkl')
    # clf = joblib.load('target.pkl')
    # print(clf.predict(X_Test))
    # print(clf.score(X_Test, Y_Test))
    return list;

def random_100(data , num) :
    return random.sample(data,num)


if __name__ == '__main__':
    generatevectors();