import json;
import os;
import urllib.request,urllib.parse;
import requests;

def main():
    f = open('test_data.json' , encoding = 'utf-8');
    res = f.read();
    data = json.loads(res);
    for item in data:
        user = data[item];
        cases = user["cases"];
        for case in cases:
            address = case["case_zip"];
            dirpath = "D:\\case\\" + case["case_type"];
            if not os.path.exists(dirpath):
                os.makedirs(dirpath);
            name = dirpath + "\\" + urllib.parse.unquote(os.path.basename(case["case_zip"]))
            request = requests.get(address, stream=True)
            file = open(name, "wb")
            for chunk in request.iter_content(chunk_size=1024):
                if chunk:
                    file.write(chunk)
                    file.flush();
            file.close();

def downloadupload(user_id):
    f = open('test_data.json', encoding='utf-8');
    res = f.read();
    data = json.loads(res);
    user = data[user_id];
    cases = user["cases"];
    for case in cases:
        upload_records = case["upload_records"];
        for upload_record in upload_records:
            address = upload_record["code_url"];
            dirpath = "D:\\"+ user_id ;
            if not os.path.exists(dirpath):
                os.makedirs(dirpath);
            name = dirpath + "\\" + urllib.parse.unquote(os.path.basename(upload_record["code_url"]))
            request = requests.get(address, stream=True)
            file = open(name, "wb")
            for chunk in request.iter_content(chunk_size=1024):
                if chunk:
                    file.write(chunk)
                    file.flush();
            file.close();

if __name__ == '__main__':
    downloadupload("3544");
