import OperatorParser
import sys

def __main__():
    print("""

Enter the number of the desired option
    """)
    menu()

def menu():
    print("1.Check updates")
    print("2.Load all operators")
    print("3.Load target operator")
    print("4.Exit")
    
    userInput = input("Input: ")

    if userInput == "1":
        checkUpdates()
    elif userInput == "2":
        loadAllOperators()
    elif userInput == "3":
        loadTargetOperator()
    elif userInput == "4":
        sys.exit(0)
    else:
        input("Error: No such option. Press Enter to continue")
        menu()

def checkUpdates():
    print("updates")

def loadAllOperators():
    print("loadAll")

def loadTargetOperator():
    print("Load target")

__main__()

