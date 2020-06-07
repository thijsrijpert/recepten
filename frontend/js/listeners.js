$(document).ready(function() {
    $("#overviewButton").on('click', function(e){
      e.preventDefault();
      $.ajax({
          url: '../renderer/timeofday.php',
          method: 'GET',
          data: {
              action: 'overview'
          },
          success: function (response) {
              $("#content").empty();
              $("<p>" + response + "</p>").appendTo("#content");
          }
      });
    });

    $("#insertButton").on('click', function(e){
      e.preventDefault();
      $.ajax({
          url: '../renderer/timeofday.php',
          method: 'GET',
          data: {
              action: 'insert'
          },
          success: function (response) {
              $("#content").empty();
              $("<p>" + response + "</p>").appendTo("#content");
          }
      });
    });

    $("#content").on('click', '#insertSubmit', function(e){
      e.preventDefault();
      $.ajax({
          url: '../service/timeofday.php',
          method: 'POST',
          data: {
              action: 'insert',
              object: {
                  name: $("#nameInput").val()
              }
          },
          success: function (response) {
              $("#content").empty();
              $("<p>" + response + "</p>").appendTo("#content");
          }
      });
    });
});
