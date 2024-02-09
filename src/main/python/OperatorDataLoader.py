import OperatorParser
import sys
import os
import json

TARGET_RELATIVE_PATH = "operatorsData"

def __main__():
    print("""

Enter the number of the desired option
    """)
    OperatorParser.loadPage()
    menu()

def menu():
    OperatorParser.setTargetPath(TARGET_RELATIVE_PATH)
    print("1.Check updates")
    print("2.Load all operators")
    print("3.Exit")
    
    userInput = input("Input: ").lower().strip()

    if userInput == "1":
        checkUpdates()
    elif userInput == "2":
        loadAllOperators()
    elif userInput == "3":
        sys.exit(0)
    else:
        input("Error: No such option. Press Enter to continue")
        menu()

def checkUpdates():
    if not os.path.exists(OperatorParser.targetPath) or not os.path.exists(OperatorParser.targetPath + "/operatorNames.json"):
        print("Operators data not found. Start to load? (y/n)")
        userInput = input("Input: ").lower().strip()
        while(userInput != "y" and userInput != "n"):
            print("Accept 'y; or 'n' (yes/no)")
            userInput = input("Input: ").lower().strip()
        if userInput == "y":
            loadAllOperators()
            menu()
            return
        if userInput == "n":
            menu()
            return
    OperatorParser.loadPage()
    operatorList = getLoadedOperators()
    listOp = OperatorParser.getExistedOperators()
    notLoadedOperators = [op for op in listOp if op not in operatorList]
    if len(notLoadedOperators) == 0:
        print("Update not required")
        menu()
        return
    
    print("Detected unloaded operators: ")
    print("------------------")
    for item in notLoadedOperators:
        print(item)
    print("------------------")
    print("Start loading...")
    print("To stop loading press CTRL + C\n")
    
    names = loadOperatorNamesJson()
    reserved = []
    
    loadedCount = 0
    for item in notLoadedOperators:
        print(f'{loadedCount}/{len(notLoadedOperators)}')
        numberInJson = getEmptyNumberInNamesJson(reserved)
        OperatorParser.loadOp(item)
        reserved.append(numberInJson)
        names[numberInJson] = item
        loadedCount += 1
    
    with open(OperatorParser.targetPath + "/operatorNames.json", "w") as json_file:
        json.dump(names, json_file, indent=2)
    print(f'\n{loadedCount}/{len(notLoadedOperators)} operators successfully loaded')
    menu()
    
def getLoadedOperators():
    directories = loadOperatorNamesJson()
    directories = list(directories.values())
    return directories
    
def loadAllOperators():
    print("To stop loading press CTRL + C")
    OperatorParser.loadAllOperators()

def loadTargetOperator():
    print("Load target")

def getEmptyNumberInNamesJson(reserved):
    names = loadOperatorNamesJson()
    keys = list(map(int, names.keys()))
    keys.sort()
    value = 0
    while(value in keys or value in reserved):
        value += 1

    return value

def loadOperatorNamesJson():
    jsonFile = []
    with open(TARGET_RELATIVE_PATH + "/operatorNames.json", "r") as file:
        jsonFile = json.load(file)
    return jsonFile

__main__()
