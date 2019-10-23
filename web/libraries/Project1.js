
var Project1 = ( function() {

    return {

        init: function() {
            
            $("#version").html( "jQuery Version: " + $().jquery );

        },
        
        submitSearchForm: function() {

            if ( $("#search").val() === "" ) {

                alert("You must enter a search parameter!  Please try again.");
                return false;

            }

            $.ajax({

                url: 'RegistrationServlet',
                method: 'GET',
                data: $('#searchform').serialize(),

                success: function(response) {

                    $("#resultsarea").html(response);

                }

            });

            return false;

        }
        
        registration: function() {
            
            $.ajax({

                url: 'RegistrationServlet',
                method: 'POST',
                data: $('#registrationform').serialize(),

                success: function(response) {

                    $("#resultsarea").html(response);

                }

            });

            return false;
            
        }

    };

}());


