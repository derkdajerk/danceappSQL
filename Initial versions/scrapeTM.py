from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.common.exceptions import NoSuchElementException

def safe_find(element, xpath, default="N/A"):
    try:
        return element.find_element(By.XPATH, xpath).text
    except NoSuchElementException:
        return default

op = webdriver.ChromeOptions()
op.add_argument('headless')
op.add_argument('--log-level=3')
driver = webdriver.Chrome(options=op) # COMMENT OUT IF NEED TO FIND XCODE
url = 'https://www.mindbodyonline.com/explore/locations/tmilly-studio'
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
    className = safe_find(element, './/h5/a')  # Query within the parent container
    Instructor = safe_find(element, './/p/a')
    Price = safe_find(element, './/span/p')
    Time = safe_find(element, './/h5[contains(@class, "has-text-primary is-marginless")]')
    Length = safe_find(element, './/p[contains(@class, "ClassTimeScheduleItemDesktop_endTime__26mcG")]', "N/A")
    
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

