# mjs-oauth-authorization-server

# Usage: #

Authorise a user and generate a token. Either use a usernamne/pwd:

    curl -i -X POST \
    -vu clientapp:123456 \
    http://localhost:9099/oauth/token \
    -H "Accept: application/json" \
    -d "password=spring&username=roy&grant_type=password&scope=read%20write&client_secret=123456&client_id=clientapp"

Or, use the 'client_credentials' flow:

    curl -i -X POST \
    -vu clientapp:123456 \
    http://localhost:9099/oauth/token \
    -H "Accept: application/json" \
    -d "grant_type=client_credentials&scope=read%20write&client_secret=123456&client_id=clientapp"


Then, you get a response (if valid) like this:

    {
      "access_token":"330bd0f8-52f5-4621-9df5-4fa51e9141dd",
      "token_type":"bearer",
      "refresh_token":"3e531f19-f404-449d-ad32-91f4773905d2",
      "expires_in":43199,
      "scope":"read write"
    }

You can use this when you call the [Resource Server](https://github.com/msamm-r7/mjs-oauth-resource-server).


