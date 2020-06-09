import zipfile

import joblib
import vector
import os
import numpy as np
def predict(user_id):
    dir_path = "D:\\" + user_id+ "\\" + user_id;
    file_list = os.listdir(dir_path)
    words = vector.generatewords()
    clf = joblib.load('target.pkl')
    list = np.empty(shape=[0,6309],dtype=int)
    for file in file_list:
        with zipfile.ZipFile(dir_path + "\\" + file, "r") as zip_file:
                with zip_file.open("main.py") as content:
                    temp_vector = [0 for i in range(0, 6309)]
                    for line in content:
                        str_line = str(line)
                        str_line = str_line.replace("\\t", " ")
                        str_line = str_line.replace("\\n", " ")
                        temp_list = str_line[2:len(str_line) - 3].split(" ")
                        for index in range(0, len(temp_list)):
                            if temp_list[index] in words:
                                temp_vector[words.index(temp_list[index])] += 1
                temp_vector = np.array(temp_vector)
                temp_vector = temp_vector.reshape(1, 6309)
                list = np.append(list, temp_vector, axis=0)
    result = clf.predict(list).tolist()
    print(max_list(result))

def max_list(list):
    temp = 0
    for i in list:
        if list.count(i) > temp:
            max_str = i
            temp = list.count(i)
    return max_str

if __name__ == '__main__':
    predict("3544");