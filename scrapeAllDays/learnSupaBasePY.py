
import os
from supabase import create_client, Client, ClientOptions

url: str = os.environ.get("SUPABASE_URL")
service_key: str = os.environ.get("SUPABASE_SERVICE_KEY")

if not url or not service_key:
    raise ValueError("Supabase URL or Service Role Key is not set in the environment!")

supabase: Client = create_client(
    url, 
    service_key,
    options=ClientOptions(
        postgrest_client_timeout=4,
        storage_client_timeout=4,
        schema="public",
    )
)

try:
    tables = supabase.table("danceClassStorage").select("count").execute()
    print("Connected to database")
except Exception as e:
    print("Connection error:", str(e))

# ...existing code...
try:
    response = (
        supabase.table("danceClassStorage")
        .select("*")
        .execute()
    )
    
    response_data = response.data
    print(f"Response data type: {type(response_data)}")
    
except Exception as e:
    print("Error retrieving rows:", str(e))
    
    
response_data = response.data

firstClass = response.data[1]
firstClassInstructor = response.data[1].get('instructor')
firstClassItemsTouple = response.data[1].items()

print(response.data)


print(f"first class type: {type(firstClass)}")
print(firstClass)
print(f"first class instructor type: {type(firstClassInstructor)}")
print(firstClassInstructor)
print(f"first class itemstouple type: {type(firstClassItemsTouple)}")
print(firstClassItemsTouple)