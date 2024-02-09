import requests
import os
import json
from bs4 import BeautifulSoup, Tag, NavigableString;

OPERATORS_DATA_PATH = './operatorsData'
# targetPath = 'src/main/resources/com/trymad/' + OPERATORS_DATA_PATH
targetPath = OPERATORS_DATA_PATH
operatorsList = {}
operatorCount = 0;

operators = []
URL = "https://www.ubisoft.com/en-us/game/rainbow-six/siege/game-info/operators"

def loadPage():
    global operators
    operatorsHtml = requests.get(URL)
    soup = BeautifulSoup(operatorsHtml.text, 'html.parser')
    operators = soup.find('div', class_='oplist__cards__wrapper')

def loadOperatorData(operatorDiv):
    global operatorCount
    opName = operatorDiv.find('span').text.strip()
    print(f"/__________{opName}__________\\")
    opFormattedName = getFormattedName(opName)
    opImgSrc = operatorDiv.find('img', class_='oplist__card__img')['src']
    opIconSrc = operatorDiv.find('img', class_='oplist__card__icon')['src']

    responseOpImage = requests.get(opImgSrc)
    responseOpIcon = requests.get(opIconSrc)
    
    pathToOpDirectory = f"{targetPath}/{opFormattedName}"
    if os.path.exists(pathToOpDirectory) == False: 
        os.makedirs(pathToOpDirectory)

    operatorsList[operatorCount] = opFormattedName
    operatorCount = operatorCount + 1

    loadOperatorImage(opFormattedName, responseOpImage, responseOpIcon)
    loadout = loadOperatorWeapons(opFormattedName)
    data = {
        "name" : opName,
        "formattedName" : opFormattedName,
        "image" : f'{opFormattedName}_Img.png',
        "icon" : f'{opFormattedName}_Icon.png',
        "loadout" : loadout
    }

    with open(pathToOpDirectory + "/data.json", "w") as json_file:
        jsonData = json.dump(data, json_file, indent=2)
        
def loadOperatorImage(operatorName, image, icon):
    print(f"Load {operatorName} images")
    with open(f"{targetPath}/{operatorName}/{operatorName}_Img.png", "wb") as file:
        file.write(image.content)
    with open(f"{targetPath}/{operatorName}/{operatorName}_Icon.png", "wb") as file:
        file.write(icon.content)
    print(f"Finish load {operatorName} images")
    
def loadOperatorWeapons(operatorName):

    loadout = {
        "primary_weapon" : {},
        "secondary_weapon" : {},
        "gadget" : {},
        "unique_ability" : {}
    } 

    print(f"Start load {operatorName} weapons...")
    operatorLoadoutHtml = requests.get(f'{URL}/{operatorName}')
    operatorDirPath = os.path.join(targetPath, operatorName)

    soup = BeautifulSoup(operatorLoadoutHtml.text, 'html.parser')
    loadoutHtml = soup.find('div', class_='operator__loadout')
    if os.path.exists(f'{operatorDirPath}/loadout') == False:
        os.makedirs(f'{operatorDirPath}/loadout')

    for weaponCategoryHtml in loadoutHtml.find_all('div', class_='operator__loadout__category'):
        weaponCategoryNameHtml = weaponCategoryHtml.find('h2', class_='operator__loadout__category__title')
        weaponCategoryName = weaponCategoryNameHtml.find('span').text.replace(' ', '_').lower().strip()

        print(f'Load category: {weaponCategoryName}')

        pathToWeaponCategory = f'{targetPath}/{operatorName}/loadout/{weaponCategoryName}'
        if os.path.exists(pathToWeaponCategory) == False:
            os.makedirs(pathToWeaponCategory)
        
        for weaponInfoHtml in weaponCategoryHtml.find('div', class_='operator__loadout__category__items'):
            weaponName = weaponInfoHtml.find_next('p').text.strip().replace(' ', '_');
            print(f'Load weapon: {weaponName}')
            formattedWeaponName = weaponName.lower().replace('"', "");

            if os.path.exists(f'{pathToWeaponCategory}/{formattedWeaponName}') == False:
                os.makedirs(f'{pathToWeaponCategory}/{formattedWeaponName}')

            weaponImageUrl = weaponInfoHtml.find('img')['src']
            weaponImage = requests.get(weaponImageUrl).content
            with open(f'{pathToWeaponCategory}/{formattedWeaponName}/{formattedWeaponName}_Image.png', 'wb') as file:
                file.write(weaponImage)

            weaponTypeHtml = weaponInfoHtml.find_all('p')
            weaponType = weaponTypeHtml[len(weaponTypeHtml) - 1].text.replace(' ', '_').lower().strip()

            if not weaponType: weaponType = "none" 


            loadout[weaponCategoryName][formattedWeaponName] = {
                "name" : weaponName,
                "type" : weaponType,
                "image" : f'{formattedWeaponName}_Image.png'
            }
            print(f'Weapon {formattedWeaponName} loaded')
        print(f'Category {weaponCategoryName} laded')
    print(f'{operatorName} weapons loaded')
    return loadout;

def getExistedOperators():
    opList = []
    for operatorDiv in operators:
        opName = operatorDiv.find('span').text.strip()
        opFormattedName = getFormattedName(opName)
        opList.append(opFormattedName)
    return opList

def loadAllOperators():
    loadedOp = 0;
    if os.path.exists(f"{targetPath}") == False: os.makedirs(f'{targetPath}')
    for operatorDiv in operators:
        print(f'{loadedOp}/70')
        loadOperatorData(operatorDiv)
        loadedOp = loadedOp + 1
    with open(targetPath + "/operatorNames.json", "w") as json_file:
        jsonData = json.dump(operatorsList, json_file, indent=2)
    print(f"{loadedOp}/70 operators successfull loaded")

def loadOp(name):
    for operatorDiv in operators:
        opName = operatorDiv.find('span').text.strip();
        opFormattedName = getFormattedName(opName)
        if opFormattedName == name:
            if os.path.exists(f"{targetPath}") == False: os.makedirs(f'{targetPath}')
            loadOperatorData(operatorDiv)
            return
    print('Operator don`t exist, or incorrect name')
    

# Загрузка изображений с оперативниками, создание папок
def getFormattedName(name):
    return name.replace('Ã', 'A').replace('ä', 'a').replace('Ø', 'O').replace('ã', 'a').replace('"', "").lower()

def setTargetPath(relativeTargetPath):
    global targetPath
    targetPath = relativeTargetPath
