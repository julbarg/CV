var jsonText;
var selectRegion;
function drawRegionsMap(xhr, status, args) {

   jsonText = document.getElementById("form_map:jsonText").value;
   var data = new google.visualization.DataTable(jsonText);

   var options = {
      region : 'CO',
      displayMode : 'regions',
      resolution : 'provinces',
      backgroundColor : '#B9DCFF',
      datalessRegionColor : '#F4F3F2',
      colorAxis : {
         colors : [ '#FF0000', '#B40404' ]
      }
   };

   var geomap = new google.visualization.GeoChart(document.getElementById('regions_div'));
   geomap.draw(data, options);
   google.visualization.events.addListener(geomap, 'regionClick', function(eventData) {
      selectRegion = eventData.region;
      selectMap([ {
         name : 'regionSelect',
         value : selectRegion
      } ]);
   });

}

var jsonTextInt;
var selectRegionInt;
function drawRegionsMapInt(xhr, status, args) {

   jsonTextInt = document.getElementById("form_map:jsonTextInt").value;
   var data = new google.visualization.DataTable(jsonTextInt);
   
   var options = {
      region : 'world',
      displayMode : 'regions',
      resolution : 'countries',
      backgroundColor : '#B9DCFF',
      datalessRegionColor : '#F4F3F2',
      colorAxis : {
         colors : [ '#FF0000', '#B40404' ]
      }
   };

   var geomapInt = new google.visualization.GeoChart(document.getElementById('regions_div_int'));
   geomapInt.draw(data, options);
   google.visualization.events.addListener(geomapInt, 'regionClick', function(eventData) {
      selectRegion = eventData.region;
      selectMapInt([ {
         name : 'regionSelect',
         value : selectRegion
      } ]);
   });

}