from flask import Flask, jsonify
import requests
from bs4 import BeautifulSoup
app = Flask(__name__)
from firebase import firebase
firebase = firebase.FirebaseApplication('https://gdsc-dce-default-rtdb.firebaseio.com/', None)

# SampleData = {
#     1: "Integration Successfull"
# }
# firebase.patch("Hui", SampleData)

# UserLater Variables
DicList = []
Organizers = []

# Firebase DataVariables
Dict_OrganizersInformation = {}
Dict_PastEventsInformation = {}
Dict_AllEventsTags = {}
EventsTagsIterator = 0

# , , , ,
#             , , , ,
#             , ;

ProjectInformation = {}
Project = {}
for item in range(10):
    Project["ProjectName"] = "Project Name"
    Project["ProjectID"] = "#09182"
    Project["ProjectSourceUrl"] = "www.github.com/Sanjeevarora3735/DBTS"
    Project["ProjectDescriptionBody"] = "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface without relying on meaningful content. Lorem ipsum may be used as a placeholder before final copy is available."
    Project["ProjectTags"] = "Android Development, Web Development, Blockchain"
    Project["ProjectOwner"] = "Someone's Name"
    Project["ProjectPosterImageUrl"] = "https://s3-ap-south-1.amazonaws.com/static.awfis.com/wp-content/uploads/2017/07/07184649/ProjectManagement.jpg"
    Project["ProjectSubmittingDate"] = "26-01-2023"
    Project["ProjectApprovalDate"] = "28-01-2023"
    Project["ProjectApprovalMentor"] = "Any Lead"
    Project["isShowcase"] = False
    Project["ProfileImageUrl"] = "https://media.istockphoto.com/id/1300972574/photo/millennial-male-team-leader-organize-virtual-workshop-with-employees-online.jpg?b=1&s=170667a&w=0&k=20&c=96pCQon1xF3_onEkkckNg4cC9SCbshMvx0CfKl2ZiYs="
    ProjectInformation[item] = Project

firebase.patch("ProjectsInformation/AllProjects", ProjectInformation)



def FetchOrganizersInformation():
    url = "https://gdsc.community.dev/dronacharya-college-of-engineering-gurugram/"
    r = requests.get(url)
    htmlContent = r.content
    soup = BeautifulSoup(htmlContent, 'html.parser')

    OrganizersInformation = (soup.find_all('li',class_="people-card"))

    FetchedKey = 0
    for item in OrganizersInformation:
        TemporaryOrganizer = {}
        TemporaryOrganizer["ImageSrc"] = "https://res.cloudinary.com" + (str(item).split("this.src='https://res.cloudinary.com")[1].split(";")[0].replace("'", ""))
        TemporaryOrganizer["Name"] = (str(item).split("<h2 class=\"people-card--name\">")[1].split("</h2")[0])    
        TemporaryOrganizer["Title"] = (str(item).split("<p class=\"people-card--title\">")[1].split("</p")[0])
        Organizers.append(TemporaryOrganizer)
        Dict_OrganizersInformation[FetchedKey] = TemporaryOrganizer
        FetchedKey+=1
    

def GetPastEvents(url, FetchedKey):
    
    url= "https://gdsc.community.dev"+url
    # Here we have to specify the url of the webpage we have to use in our web scrapping
    # In our case we have the event link 
    # url = "https://gdsc.community.dev/events/details/developer-student-clubs-dronacharya-college-of-engineering-gurugram-presents-introductory-session-hack-o-relay/"

    # Now we have to get the content from the provided url 
    r = requests.get(url)

    # Here we convert / get the HTML content from the fetch data 
    htmlContent = r.content

    # Now this is the time to use BeautifulSoup to parse the html.parser for the content
    soup = BeautifulSoup(htmlContent, 'html.parser')

    # Creating a Dict type Variable, which we have to use later to provide the Json Object
    EventDetails = {}
    EventDetails["AccessedUrl"] = url

    # Finding the Event Title From The Provided Url & Put it into the Dict (EventDetails)
    EventTitle = ((soup.find("title").getText()).split("See")[1]).split(" at ")[0]
    EventDetails["EventTitle"] = EventTitle

    # Finding the College Name From The Provided Url & Put it into the Dict (EventDetails)
    EventCollegeName = (soup.find("title").getText()).split(" at ")[1]
    EventDetails["EventCollegeName"] = EventCollegeName.replace("\n", "")

    # Finding the Event Date & Time From The Provided Url & Put it into the Dict (EventDetails)
    EventTimeDetails = (soup.find('div',class_="event-date-time").getText())
    EventDetails["EventTimings"] = EventTimeDetails

    EventDescription = (soup.find('div', class_="event-description").getText()).replace("\n", "")
    EventDetails["EventDescription"] = EventDescription


    # Finding the Event Tags From The Provided Url & Put it into the Dict (EventDetails)
    EventTags = ""
    try:    
        EventTags = ((str)(htmlContent)).split("event_tags")[1]
        EventTags =EventTags.split("]")[0].split("[")[1].replace("\"", "")
    except:
        pass
    finally:
        EventDetails["EventTags"] = EventTags

    # Finding the Event Thumbnail (if available) From The Provided Url & Put it into the Dict (EventDetails)

    EventThumbnail = (str(htmlContent))
    EventThumbnail = EventThumbnail.split("Globals.featured_video_url")[1]
    EventThumbnail = EventThumbnail.split(";")[0]
    EventThumbnail = EventThumbnail.replace("'", "")
    EventThumbnail = EventThumbnail.replace("\\","")
    EventThumbnail = EventThumbnail.replace("&amp","")
    if(EventThumbnail.__contains__("youtu.be")):
        EventThumbnail = EventThumbnail.split("https://youtu.be/")[1]
        EventDetails["Youtube"] = "https://img.youtube.com/vi/"+EventThumbnail+"/maxresdefault.jpg"
    elif (EventThumbnail.__contains__("youtube.com")):
        EventThumbnail = EventThumbnail.split("https://www.youtube.com/watch?v=")[1]
        EventDetails["Youtube"] = "https://img.youtube.com/vi/"+EventThumbnail+"/maxresdefault.jpg"

    
    # No Thumbnail Found Condition == True
    if(EventThumbnail.__contains__("None")):
        EventDetails["Youtube"] = "https://cdn.dribbble.com/users/2147426/screenshots/9926785/media/60b48eeefe7af7079270bcb08081e39a.png?compress=1&resize=400x300"
    else:
        DicList.append(EventDetails)
        Dict_PastEventsInformation[FetchedKey] = (EventDetails)
        Dict_AllEventsTags[EventsTagsIterator] = EventTags




def FetchPastEventsDetails():
    url = "https://gdsc.community.dev/dronacharya-college-of-engineering-gurugram/"
    requestUrl = requests.get(url)
    htmlContent = requestUrl.content
    soup = BeautifulSoup(htmlContent, 'html.parser')
    PastEventList = soup.find_all('ul',class_='past-event-list')
    AllAnchor = PastEventList[0].find_all('a')
    i = 0
    Sample = {}
    for event in AllAnchor:
        event_information = event.get("href")
        Sample[i] = event_information
        i+=1
        GetPastEvents(event_information,i-1)

# Page where the check if page is running or not
@app.route("/")
def hello_world():
    return  "<h1>Hello World</h1>"


# Page where the Event details will we display
@app.route("/EventOverview")
def EventOverview():
    FetchPastEventsDetails()  
    # Apply All Changes To The Firebase ...
    firebase.patch("EventsInformation/PastEvents", Dict_PastEventsInformation)
    return  jsonify(DicList)


# Page where the Event details will we display
@app.route("/Organisers")
def Organisers():
    FetchOrganizersInformation()
    # Apply All Changes To The Firebase ...
    firebase.patch("OrganizersInformation/Members", Dict_OrganizersInformation)

    return  jsonify(Organizers)



