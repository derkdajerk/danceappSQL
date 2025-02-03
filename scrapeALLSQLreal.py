# took 17-20 seconds for just the 3 scrapes alone to run like this

import mysql.connector
import time
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.common.exceptions import NoSuchElementException
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

start = time.time()
#connect to sql database
#EXTRA TEST
mydb = mysql.connector.connect(
  host="localhost",
  user="dance",
  password="5678",
  database="danceappstorage"
)

mycursor = mydb.cursor()

def safe_find(element, xpath, default="N/A"):
    try:
        return element.find_element(By.XPATH, xpath).text
    except NoSuchElementException:
        return default

def scrape(url):
    start = time.time()
    op = webdriver.ChromeOptions()
    op.add_argument('headless')
    op.page_load_strategy = 'eager'
    op.add_argument('--log-level=3')
    driver = webdriver.Chrome(options=op) # COMMENT OUT IF NEED TO FIND XCODE
    driver.get(url)
    WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.XPATH, '//div[contains(@class, "columns is-vcentered ClassTimeScheduleItemDesktop_separator__1vvuL")]')))
    try: # Locate all class containers
        elements = driver.find_elements(By.XPATH, '//div[contains(@class, "columns is-vcentered ClassTimeScheduleItemDesktop_separator__1vvuL")]')
    except NoSuchElementException:
        print("Class containers not found")
        elements = []
    try:
        Date = driver.find_element(By.XPATH, '//h5[contains(@class, "is-marginless")]').text
        Date = Date[11:len(Date)]
    except NoSuchElementException:
        Date = "N/A"
    class_list = []
    # Iterate over each class container
    for element in elements:
        className = safe_find(element, './/a[contains(@class, "ClassTimeScheduleItemDetails_classLink__1tyYz")]')  # Query within the parent container
        Instructor = safe_find(element, './/a[contains(@class, "ClassTimeScheduleItemDetails_link__1gju5")]')
        Price = safe_find(element, './/p[contains(@class, "Price_price__295Er Price_priceFont__1nZCw")]')
        Time = safe_find(element, './/h5[contains(@class, "has-text-primary is-marginless")]')
        Length = safe_find(element, './/p[contains(@class, "ClassTimeScheduleItemDesktop_endTime__26mcG")]', "N/A").replace("(", "").replace(")", "")
        class_item = (
            className,
            Instructor,
            Price,
            Time,
            Length,
            Date
        )
        class_list.append(class_item)
    end = time.time()
    length = end - start
    print("\nIt took", length, "seconds!")
    return class_list

mycursor.execute("DELETE FROM mdc")
mycursor.execute("ALTER TABLE mdc AUTO_INCREMENT = 1")
mycursor.execute("DELETE FROM ml")
mycursor.execute("ALTER TABLE ml AUTO_INCREMENT = 1")
mycursor.execute("DELETE FROM tmilly")
mycursor.execute("ALTER TABLE tmilly AUTO_INCREMENT = 1")
mydb.commit()  # Commit the changes to the database
tmilly = 'https://www.mindbodyonline.com/explore/locations/tmilly-studio'
mL = 'https://www.mindbodyonline.com/explore/locations/movement-lifestyle-noho'
mdc = 'https://www.mindbodyonline.com/explore/locations/millennium-dance-complex-studio-city'

val = scrape(tmilly)
val2 = scrape(mL)
val3 = scrape(mdc)
sql = "INSERT INTO tmilly (classname, instructor, price, time, length, date) VALUES (%s, %s, %s, %s, %s, %s)"
sql2 = "INSERT INTO ml (classname, instructor, price, time, length, date) VALUES (%s, %s, %s, %s, %s, %s)"
sql3 = "INSERT INTO mdc (classname, instructor, price, time, length, date) VALUES (%s, %s, %s, %s, %s, %s)"

print("Entering data to SQL Tables:")
mycursor.executemany(sql, val)
print("Completed tmilly:")
mycursor.executemany(sql2, val2)
print("Completed ml:")
mycursor.executemany(sql3, val3)
print("Completed mdc:")

mydb.commit()

print(mycursor.rowcount, "was inserted.")
    
end = time.time()
length = end - start
# Show the results : this can be altered however you like
print("\nIt took in total", length, "seconds!")