# Personal Diary Web App




## Deployment

To deploy this project run, You'll need Java 17 installed.

```bash
  ./gradlew bootRun
```

Here  I have used JWT token base authentication. So, If you want to explore all api, then you first need to 
do signup. Then you have to log in for getting the token.

```http
  POST /signup
```

| Parameter | Type     | 
| :-------- | :------- | 
| `username` | `string` |
| `email` | `string` |
| `password` | `string` |

Response
```bash
{
    "result": true,
    "message": "Registered Successfully"
}
```

```http
  POST /login
```

| Parameter | Type     | 
| :-------- | :------- | 
| `username` | `string` |
| `password` | `string` |

Response
```bash
  {
    "authenticationToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcmlmIiwiYXV0aCI6W3siYXV0aG9yaXR5IjoiUk9MRV9VU0VSIn1dLCJpYXQiOjE2NTU1Njk3NzQsImV4cCI6MTY1NTU3MzM3NH0.DEx7TrqId4t5lWlXTZ10k3IOnw8o_YtNf6TnJXeI47A",
    "username": "arif"
  }
```

After login, Your have to use authorization token for using all api. Example.
```http
  GET localhost:8080/api/notes
```
For Header

| Parameter | value    | 
| :-------- |:---------| 
| `Authorization` | `Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcmlmIiwiYXV0aCI6W3siYXV0aG9yaXR5IjoiUk9MRV9VU0VSIn1dLCJpYXQiOjE2NTU1NzAyNjUsImV4cCI6MTY1NTU3Mzg2NX0.rWZ-JdEuChLtZtrHFIZdOHPLFEqS_1KoLJTOrlkdDLU` |

Response
```bash
  [
    {
        "id": 1,
        "title": "1st Task",
        "content": "This is 2nd note",
        "lastUpdatedTime": "17-06-2022",
        "category": {
            "id": 2,
            "name": "Horror"
        }
    }
]
```

Other api list
```http
GET /api/notes # get all notes of current logged in user
```
```http
GET /api/notes/5 # get note with id 5 which belongs to current logged in user
```

```http
POST /api/notes {note data} # create new note

Request Body
{
    "title" : "note 1",
    "categoryId" : 2,
    "content" : "Creating note for test"
}
```

```http
PUT /api/notes/3 {note data} # patch note id 3

Request Body
{
    "title" : "note 1",
    "categoryId" : 3,
    "content" : "Changing categoryId"
}
```

```http
DELETE /api/notes/3 # delete note id 3
```

```http
GET /api/notes?query=budget&fi=title 
# search for keyword budget on title field and return matched notes
```

```http
GET /api/notes?query=budget&fi=content
# search for keyword budget on content field and return matched notes
```

```http
GET /api/categories # get list of categories belonging to logged in user
```

```http
GET /api/notes/categories/2 # get list of notes belonging to category id 2
```

```http
GET /api/notes/categories/2?query=budget 
# get list of notes belonging to category id 2 having keyword budget matching on either title or content field
```

```http
POST /api/categories # create new category

Request Body
{
    "name" : "test-category"
}
```

```http
PUT /api/categories/3 {category data} # update category id 3 with category data

Request Body
{
    "name" : "new-category"
}
```

```http
DELETE /api/categories/3 # delete category 3 and all notes with that category
```
