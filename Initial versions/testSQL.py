import mysql.connector

mydb = mysql.connector.connect(
  host="localhost",
  user="dance",
  password="5678",
  database="danceappstorage"
)

print(mydb)

mycursor = mydb.cursor()

mycursor.execute("SHOW DATABASES")

for x in mycursor:
  print(x)
  
# mycursor.execute("CREATE TABLE tmilly (id INT AUTO_INCREMENT PRIMARY KEY, classname VARCHAR(255), instructor VARCHAR(255), price VARCHAR(255), time VARCHAR(255), length VARCHAR(255))")

mycursor.execute("SHOW TABLES")

print("tables\n")
for x in mycursor:
  print(x)
  
mycursor.execute("INSERT INTO tmilly (classname, instructor, price, time, length, date)")
  
mycursor.execute("SELECT * FROM tmilly")

for x in mycursor:
  print(x)
  

sql = "INSERT INTO tmilly (classname, instructor, price, time, length, date) VALUES (%s, %s, %s, %s, %s, %s)"
val = (className, Instructor, Price, Time, Length, Date)
mycursor.execute(sql, val)