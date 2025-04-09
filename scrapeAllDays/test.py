from datetime import datetime

date_str = "Monday, April 7"
date_obj = datetime.strptime(date_str + f", {datetime.now().year}", "%A, %B %d, %Y").date()

print(date_obj)  # Outputs: 2025-04-07 00:00:00