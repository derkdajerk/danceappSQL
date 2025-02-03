from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.common.exceptions import NoSuchElementException

op = webdriver.ChromeOptions()
op.add_argument('headless')
op.add_argument('--log-level=3')
driver = webdriver.Chrome(options=op) # COMMENT OUT IF NEED TO FIND XCODE
url = 'https://www.mindbodyonline.com/explore/locations/movement-lifestyle-noho'
driver.get(url)
driver.implicitly_wait(3)

try: # Locate all class containers
    elements = driver.find_elements(By.XPATH, '//div[contains(@class, "columns is-vcentered ClassTimeScheduleItemDesktop_separator__1vvuL")]')
except NoSuchElementException:
    print("Class containers not found")
    elements = []

class_list = []

try:
    Date = driver.find_element(By.XPATH, '//h5[contains(@class, "is-marginless")]').text
    Date = Date[11:len(Date)]
except NoSuchElementException:
    Date = "N/A"

# Iterate over each class container
for element in elements:
    try:
        className = element.find_element(By.XPATH, './/h5/a').text
    except NoSuchElementException:
        className = "N/A"

    try:
        Instructor = element.find_element(By.XPATH, './/p/a').text
    except NoSuchElementException:
        Instructor = "N/A"

    try:
        Price = element.find_element(By.XPATH, './/span/p').text
    except NoSuchElementException:
        Price = "N/A"

    try:
        Time = element.find_element(By.XPATH, './/h5[contains(@class, "has-text-primary is-marginless")]').text
    except NoSuchElementException:
        Time = "N/A"

    try:
        Length = element.find_element(By.XPATH, './/p[contains(@class, "is-marginless has-text-weight-normal ClassTimeScheduleItemDesktop_endTime__26mcG")]').text
        Length = Length[1:(len(Length) - 1)]
    except NoSuchElementException:
        Length = "N/A"

    # Add the details to the list
    class_item = {
        'Class Name': className,
        'Instructor': Instructor,
        'Price': Price,
        'Time': Time,
        'Length': Length,
        'Date': Date
    }
    class_list.append(class_item)

for Class in class_list:
     print(Class)
     print()
