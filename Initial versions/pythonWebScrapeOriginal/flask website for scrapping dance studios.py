from flask import Flask, render_template, request, jsonify
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.common.exceptions import NoSuchElementException
import os

app = Flask(__name__)

# URLs for the three websites
URLS = {
    "TMilly": "https://www.mindbodyonline.com/explore/locations/tmilly-studio",
    "ML": "https://www.mindbodyonline.com/explore/locations/movement-lifestyle-noho",
    "MDC": "https://www.mindbodyonline.com/explore/locations/millennium-dance-complex-studio-city",
}

def safe_find(element, xpath, default="N/A"):
    try:
        return element.find_element(By.XPATH, xpath).text
    except NoSuchElementException:
        return default

def scrape(url):
    op = webdriver.ChromeOptions()
    op.add_argument('headless')
    op.add_argument('--log-level=3')
    driver = webdriver.Chrome(options=op)
    driver.get(url)
    driver.implicitly_wait(3)

    try:  # Locate all class containers
        elements = driver.find_elements(By.XPATH, '//div[contains(@class, "columns is-vcentered ClassTimeScheduleItemDesktop_separator__1vvuL")]')
    except NoSuchElementException:
        elements = []

    try:
        Date = driver.find_element(By.XPATH, '//h5[contains(@class, "is-marginless")]').text
        Date = Date[11:len(Date)]
    except NoSuchElementException:
        Date = "N/A"

    class_list = []
    for element in elements:
        className = safe_find(element, './/a[contains(@class, "ClassTimeScheduleItemDetails_classLink__1tyYz")]')  # Query within the parent container
        Instructor = safe_find(element, './/a[contains(@class, "ClassTimeScheduleItemDetails_link__1gju5")]')
        Price = safe_find(element, './/p[contains(@class, "Price_price__295Er Price_priceFont__1nZCw")]')
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

    driver.quit()
    return class_list

@app.route('/')
def index():
    # Render the index page with options
    return render_template('index.html', urls=URLS)

@app.route('/scrape', methods=['POST'])
def scrape_selected():
    # Get selected options from the form
    selected_sites = request.form.getlist('sites')
    if not selected_sites:
        return jsonify({"error": "No sites selected"}), 400

    results = {}
    for site in selected_sites:
        url = URLS.get(site)
        if url:
            results[site] = scrape(url)

    return render_template('results.html', results=results)

if __name__ == '__main__':
    debug_mode = os.getenv('FLASK_DEBUG', 'False').lower() in ['true', '1', 't']
