import mysql.connector
from mysql.connector import Error
import time
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.common.exceptions import NoSuchElementException, TimeoutException
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

# Database connection function
def create_connection():
    try:
        mydb = mysql.connector.connect(
            host="localhost",
            user="dance",
            password="5678",
            database="danceappstorage"
        )
        if mydb.is_connected():
            print("Successfully connected to MySQL database")
            return mydb, mydb.cursor()
    except Error as e:
        print(f"Error while connecting to MySQL: {e}")
        return None

# Create a connection instance
mydb, mycursor = create_connection()

def search_by_time_window(start_time, end_time):
    if not mycursor or not mydb.is_connected():
        print("No database connection")
        return
    
    table_names = ['tmilly', 'mdc', 'ml']
    all_results = {}
    date = None
    try:
        # Query each table
        for table in table_names:
            query = f"""
            SELECT classname, instructor, price, time, length, date 
            FROM {table}
            WHERE STR_TO_DATE(time, '%l:%i %p') 
            BETWEEN STR_TO_DATE(%s, '%l:%i %p') 
            AND STR_TO_DATE(%s, '%l:%i %p')
            ORDER BY STR_TO_DATE(time, '%l:%i %p');
            """
            
            mycursor.execute(query, (start_time, end_time))
            results = mycursor.fetchall()
            
            if results:
                all_results[table] = results
                if not date:
                    date = results[0][5]
        
        if not all_results:
            print(f"\nNo classes available between {start_time} and {end_time}")
            return

        # Print date once at the top
        print(f"\n================ {date} ================")

        # Print each studio's results
        for table in table_names:
            studio_name = table.upper()
            print(f"\n=============== {studio_name} CLASSES ===============")
            
            if table not in all_results:
                print("No classes available during this time window")
                continue
            
            # Print each class in the studio
            for class_info in all_results[table]:
                time, classname, instructor, length = class_info[3], class_info[0], class_info[1], class_info[4]
                print(f"{time} | {classname:<20} | {instructor:<20} | {length}")           
    except Error as e:
        print(f"Error executing query: {e}")
        return

search_by_time_window('8:00 AM', '9:00 PM')