import os
from supabase import create_client, ClientOptions

URL, API = os.environ["SUPABASE_URL"], os.environ["SUPABASE_SERVICE_KEY"]

# 1) create the user with metadata
admin = create_client(URL, API, options=ClientOptions(schema="public"))
# admin.auth.admin.create_user({
#     "email": "derek@derektrauner.com",
#     "password": "scraper123",
#     "user_metadata": {"role": "scraper"},
#     "email_confirm": True
# })

# 2) sign in as that user to get their JWT
user_client = create_client(URL, API)
res = user_client.auth.sign_in_with_password({
    "email": "derek@derektrauner.com",
    "password": "scraper123"
})
token = res.session.access_token

# 3) attach JWT so RLS applies
user_client.postgrest.auth(token)

# 4) perform insert – policy WILL check `role = 'scraper'`
resp = user_client.table("danceClassStorage").insert({
    "classname": "Hip-Hop 101",
}).execute()

print(resp)
