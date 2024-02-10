import OperatorParser
import sys
import os
import json

TARGET_RELATIVE_PATH = "operatorsData"
OPERATOR_LIST_JSON_PATH = TARGET_RELATIVE_PATH + "/operatorNames.json"

def __main__():
    OperatorParser.loadPage()
    OperatorParser.setTargetPath(TARGET_RELATIVE_PATH)
    checkUpdates()

def checkUpdates():
    if not os.path.exists(OperatorParser.targetPath) or not os.path.exists(OPERATOR_LIST_JSON_PATH):
        print("Operators data not found. Start loading...")
        os.mkdir("operatorsData")
        emptyJson = {}
        with open(OperatorParser.targetPath + "/operatorNames.json", "w") as file:
            json.dump(emptyJson, file, indent=2)

    operatorList = getLoadedOperators()
    listOp = OperatorParser.getExistedOperators()
    notLoadedOperators = [op for op in listOp if op not in operatorList]
    if len(notLoadedOperators) == 0:
        print("You have actual operators list")
        input("")
        return
    
    print("Detected unloaded operators: ")
    print("------------------")
    for item in notLoadedOperators:
        print(item)
    print("------------------")
    print("Start loading...")
    print("To stop loading press CTRL + C\n")
    
    reserved = []
    names = loadOperatorNamesJson()
    
    loadedCount = 0
    
    for item in notLoadedOperators:
        print(f'{loadedCount}/{len(notLoadedOperators)}')
        numberInJson = getEmptyNumberInNamesJson(reserved)
        OperatorParser.loadOp(item)
        reserved.append(numberInJson)
        names[numberInJson] = item
        loadedCount += 1
        with open(OPERATOR_LIST_JSON_PATH, "w") as json_file:
            json.dump(names,json_file, indent=2)
    
    # with open(OPERATOR_LIST_JSON_PATH, "w") as json_file:
    #     json.dump(names, json_file, indent=2)
    print(f'\n{loadedCount}/{len(notLoadedOperators)} operators successfully loaded')
    input("")
    
def getLoadedOperators():
    namesDict = loadOperatorNamesJson()
    return list(namesDict.values())
    
def getEmptyNumberInNamesJson(reserved):
    names = loadOperatorNamesJson()
    keys = list(map(int, names.keys()))
    keys.sort()
    value = 0
    while(value in keys or value in reserved):
        value += 1

    return value

def loadOperatorNamesJson():
    jsonFile = {}
    with open(OPERATOR_LIST_JSON_PATH, "r") as file:
        jsonFile = json.load(file)
    return jsonFile

__main__()
