from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.common.exceptions import NoSuchElementException
import time
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

def safe_find(element, xpath, default="N/A"):
    try:
        return element.find_element(By.XPATH, xpath).text
    except NoSuchElementException:
        return default

start = time.time()

op = webdriver.ChromeOptions()
op.add_argument('headless')
op.page_load_strategy = 'eager'
op.add_argument('--log-level=3')
driver = webdriver.Chrome(options=op) # COMMENT OUT IF NEED TO FIND XCODE
url = 'https://www.mindbodyonline.com/explore/locations/tmilly-studio'
driver.get(url)
WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.XPATH, '//div[contains(@class, "columns is-vcentered ClassTimeScheduleItemDesktop_separator__1vvuL")]')))

try: # Locate all class containers
    elements = driver.find_elements(By.XPATH, '//div[contains(@class, "columns is-vcentered ClassTimeScheduleItemDesktop_separator__1vvuL")]')
    # # elements = driver.find_elements(By.CSS_SELECTOR, 'div.HorizontalLine_wrapper__1HZEP h5')
    # elements = driver.find_elements(By.CSS_SELECTOR, '#root > main > div:nth-child(3)')
    # print(len(elements))  # See if any elements are found
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
    Length = safe_find(element, './/p[contains(@class, "ClassTimeScheduleItemDesktop_endTime__26mcG")]', "N/A").replace("(", "").replace(")", "")
    
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

end = time.time()
length = end - start

# Show the results : this can be altered however you like
print("\nIt took", length, "seconds!")
