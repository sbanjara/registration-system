
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

                url: 'registration',
                method: 'GET',
                data: $('#searchform').serialize(),

                success: function(response) {
                    $("#resultsarea").html(response);
                }
            });

            return false;

        },
        
        registration_confirmation: function(result) { 
            
            var s = "<p>Congratulations! You have successfully registered as: <strong>" + result["displayname"] + "</strong></p>";
            s += "<p>Your registration code is: <strong>" + result["registration_code"] + "</strong></p>";
            $("#output").html(s);
            
        },
        
        registration: function() {
            
            var that = this;
            
            $.ajax({

                url: 'registration',
                method: 'POST',
                data: $('#registrationform').serialize(),
                dataType: 'json',

                success: function(response) {
                    that.registration_confirmation(response);
                }

            });          
        }

    };

}());


