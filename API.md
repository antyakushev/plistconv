**Convert**
----
  Convert plist from one format to another

* **URL** 
  /convert?inp=:input&out=:output

* **Method:**
  `POST`
  
*  **URL Params**
 
   `inp=[xml|bin]` - input format (required)
   
   `out=[xml|bin]` - output format (required)


* **Data Params**
  plist content payload

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** (converted plist data)
 
* **Error Response:**

  * **Code:** `415 UNSUPPORTED_TYPE` (invalid input or output param)

  OR

  * **Code:** `400 BAD_REQUEST` (invalid plist payload)

* **Sample Call:**

     curl -X "POST" "/convert?inp=bin&out=xml" -i --data-binary "@test1_bin.plist"
