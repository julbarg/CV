var waypts = [];
var markers = [];
var map;
function initialize() {
   loadMapOrigin();
}

function loadMapOrigin() {
   var mapCanvas = document.getElementById('map-canvas');
   var mapOptions = {
      scrollwheel : false,
      center : new google.maps.LatLng(4.1156602, -72.953885),
      zoom : 5,
      mapTypeId : google.maps.MapTypeId.ROADMAP
   }

   map = new google.maps.Map(mapCanvas, mapOptions);
   var rendererOptions = {
      draggable : true
   };

   var lag = document.getElementById("form_step_two:lat").value;
   var lng = document.getElementById("form_step_two:lng").value;

   if (lag != "" && lng != "") {
      var currentLatlng = new google.maps.LatLng(lag, lng);
      var currentMarker = new google.maps.Marker({
         position : currentLatlng,
         map : map,
      });
      markers.push(currentMarker);
      map.setZoom(15);
      map.setCenter(currentMarker.getPosition());
   }

   google.maps.event.addListener(map, 'rightclick', function(e) {
      deleteMarkers();
      var markerManual = new google.maps.Marker({
         map : map,
         draggable : true,
         position : e.latLng,
         animation : google.maps.Animation.DROP
      });
      markers.push(markerManual);
      document.getElementById("form_step_two:lat").value = markerManual.position.lat();
      document.getElementById("form_step_two:lng").value = markerManual.position.lng();
   });

   // Create the search box and link it to the UI element.
   var input = /** @type {HTMLInputElement} */
   (document.getElementById('pac-input'));
   map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);

   var searchBox = new google.maps.places.SearchBox(
   /** @type {HTMLInputElement} */
   (input));

   // Listen for the event fired when the user selects an item from the
   // pick list. Retrieve the matching places for that item.
   google.maps.event.addListener(searchBox, 'places_changed', function() {
      var places = searchBox.getPlaces();

      if (places.length == 0) {
         return;
      }
      for (var i = 0, marker; marker = markers[i]; i++) {
         marker.setMap(null);
      }

      // For each place, get the icon, place name, and location.
      markers = [];
      var bounds = new google.maps.LatLngBounds();
      for (var i = 0, place; place = places[i]; i++) {
         var image = {
            url : place.icon,
            size : new google.maps.Size(71, 71),
            origin : new google.maps.Point(0, 0),
            anchor : new google.maps.Point(17, 34),
            scaledSize : new google.maps.Size(25, 25)
         };

         deleteMarkers();

         // Create a marker for each place.
         var marker = new google.maps.Marker({
            map : map,
            icon : image,
            title : place.name,
            draggable : true,
            position : place.geometry.location,
            animation : google.maps.Animation.DROP
         });

         document.getElementById("form_step_two:lat").value = marker.position.lat();
         document.getElementById("form_step_two:lng").value = marker.position.lng();

         google.maps.event.addListener(marker, 'drag', function(event) {
            document.getElementById("form_step_two:lat").value = event.latLng.lat();
            document.getElementById("form_step_two:lng").value = event.latLng.lng();
         });

         google.maps.event.addListener(marker, 'dragend', function(event) {
            document.getElementById("form_step_two:lat").value = event.latLng.lat();
            document.getElementById("form_step_two:lng").value = event.latLng.lng();
         });

         markers.push(marker);

         bounds.extend(place.geometry.location);
      }

      map.fitBounds(bounds);
      map.setZoom(15);
   });

   // Bias the SearchBox results towards places that are within the bounds of the
   // current map's viewport.
   google.maps.event.addListener(map, 'bounds_changed', function() {
      var bounds = map.getBounds();
      searchBox.setBounds(bounds);
   });

}

// Sets the map on all markers in the array.
function setAllMap(map) {
   for (var i = 0; i < markers.length; i++) {
      markers[i].setMap(map);
   }
}

// Removes the markers from the map, but keeps them in the array.
function clearMarkers() {
   setAllMap(null);
}

// Shows any markers currently in the array.
function showMarkers() {
   setAllMap(map);
}

// Deletes all markers in the array by removing references to them.
function deleteMarkers() {
   clearMarkers();
   markers = [];
}
