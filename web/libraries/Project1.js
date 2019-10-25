
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

        },
        
        get_code: function(result) {
            
            var s = "<p>Congratulations! You have successfully registered as: " + result["displayname"] + "</p>";
            s += "<p>Your registration code is: " + result["registration_code"] + "</p>";
            $("#output").html(s);
            
        },
        
        registration: function() {
            
            var that = this;
            
            $.ajax({

                url: 'RegistrationServlet',
                method: 'POST',
                dataType: 'json',

                success: function(response) {

                    that.get_code(response);

                }

            });
            
        }

    };

}());


