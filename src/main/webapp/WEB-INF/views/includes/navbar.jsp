<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url var="welcomeUrl" value="/" />

<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
           <li><a id="navWelcomeLink" href="${welcomeUrl}">Welcome</a></li>
              <c:url var="eventsUrl" value="/events/" />
              <li><a id="navEventsLink" href="${eventsUrl}">All Events</a></li>
              <c:url var="myEventsUrl" value="/events/my" />
              <li><a id="navMyEventsLink" href="${myEventsUrl}">My Events</a></li>
              <c:url var="createEventUrl" value="/events/form" />
              <li><a id="navCreateEventLink" href="${createEventUrl}">Create Event</a></li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
            <li><a href="#">Link</a></li>
            <li><a href="#">Link</a></li>
            <li><a href="#">Link</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>