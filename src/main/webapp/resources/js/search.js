var jsonText;
var selectRegion;
function drawRegionsMap(xhr, status, args) {
   var URL = "/CV/javax.faces.resource/mapTest.json.xhtml?ln=js";
   jsonData = $.ajax({
      url : URL,
      dataType : "json",
      async : false
   }).responseText;

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