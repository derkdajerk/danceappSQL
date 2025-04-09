from datetime import datetime

def convert_12h_to_24h_string(time_str):
    # Parse string like "8:30am" to a time object
    return datetime.strptime(time_str.strip().lower(), "%I:%M%p").time().isoformat()

# Example
original = "8:30pm"
converted = convert_12h_to_24h_string(original)
print(converted)  # '20:30:00'

# Then insert into Supabase as a string
data = {
    "class_time": converted  # Supabase TIME column
}

def convert_24h_to_12h_string(time_str):
    return datetime.strptime(time_str, "%H:%M:%S").strftime("%I:%M%p").lstrip("0").lower()

# Example
print(convert_24h_to_12h_string("20:30:00"))  # '8:30pm'