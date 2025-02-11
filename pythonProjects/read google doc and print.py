from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.common.exceptions import NoSuchElementException

def decode_message(url):
    op = webdriver.ChromeOptions()
    op.add_argument('headless')
    driver = webdriver.Chrome(options=op) # COMMENT OUT IF NEED TO FIND XCODE
    url = 'https://docs.google.com/document/d/e/2PACX-1vShuWova56o7XS1S3LwEIzkYJA8pBQENja01DNnVDorDVXbWakDT4NioAScvP1OCX6eeKSqRyzUW_qJ/pub'
    driver.get(url)

    try:
        c3 = driver.find_elements(By.XPATH, '//*[@id="contents"]/div/table/tbody/tr')
    except NoSuchElementException:
        c3 = "No such element"

    lines = []

    for element in c3:
        try:
            c2xcor = element.find_element(By.XPATH, './/td[1]')
            try:
                xcor = c2xcor.find_element(By.XPATH, './/p/span').text
            except NoSuchElementException:
                xcor = "xcor can not be found"
        except NoSuchElementException:
            c2xcor = "c2 not found"

        try:
            c2char = element.find_element(By.XPATH, './/td[2]')
            try:
                char = c2char.find_element(By.XPATH, './/p/span').text
            except NoSuchElementException:
                char = "xcor can not be found"
        except NoSuchElementException:
            c2char = "c2 not found"
        
        try:
            c2ycor = element.find_element(By.XPATH, './/td[3]')
            try:
                ycor = c2ycor.find_element(By.XPATH, './/p/span').text
            except NoSuchElementException:
                ycor = "xcor can not be found"
        except NoSuchElementException:
            c2ycor = "c2 not found"
        
        line_item = {
            "X-Cor" : xcor,
            "Char" : char,
            "Y-Cor" : ycor
        }
        lines.append(line_item)

    lines.pop(0)

    def findMaxXCor():
        maxXCor = 0
        for i in range(len(lines)):
            if int(lines[i]["X-Cor"]) > maxXCor:
                maxXCor = int(lines[i]["X-Cor"])
            else:
                pass
        return maxXCor

    def findMaxYCor():
        maxYCor = 0
        for i in range(len(lines)):
            if int(lines[i]["Y-Cor"]) > maxYCor:
                maxYCor = int(lines[i]["Y-Cor"])
            else:
                pass
        return maxYCor

    for y in range(findMaxYCor(), -1, -1):
        for x in range(0, findMaxXCor() + 1):
            found = False  
            for item in lines:
                    item_x = int(item["X-Cor"])
                    item_y = int(item["Y-Cor"])
                    char = item["Char"]
                    if item_x == x and item_y == y:
                        print(char, end="")
                        found = True
                        break
            if not found:
                print(" ", end="")
        print()

decode_message("https://docs.google.com/document/d/e/2PACX-1vShuWova56o7XS1S3LwEIzkYJA8pBQENja01DNnVDorDVXbWakDT4NioAScvP1OCX6eeKSqRyzUW_qJ/pub")