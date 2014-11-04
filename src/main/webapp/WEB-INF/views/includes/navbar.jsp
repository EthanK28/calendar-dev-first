<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="nav-bar" class="navbar navbar-fixed-top">
  <div class="navbar-inner">
    <div class="container">
      <c:url var="welcomeUrl" value="/" />
      <a class="brand" href="${welcomeUrl}">myCalendar</a>
      <div class="nav-collapse">
          <ul class="nav">
              <li><a id="navWelcomeLink" href="${welcomeUrl}">Welcome</a></li>
              <c:url var="eventsUrl" value="/events/" />
              <li><a id="navEventsLink" href="${eventsUrl}">All Events</a></li>
              <c:url var="myEventsUrl" value="/events/my" />
              <li><a id="navMyEventsLink" href="${myEventsUrl}">My Events</a></li>
              <c:url var="createEventUrl" value="/events/form" />
              <li><a id="navCreateEventLink" href="${createEventUrl}">Create Event</a></li>
            </ul>
      </div>
      <div id="nav-account" class="nav-collapse pull-right">
          <ul class="nav">

          </ul>
      </div>
    </div>
  </div>
</div>