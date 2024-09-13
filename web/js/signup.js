async function signup() {
    const popup = Notification({
        position: 'bottom-right',
        duration: 4000,
        isHidePrev: false,
        isHideTitle: false,
        maxOpened: 3,
    });

    var fname = document.getElementById("fname").value;
    var lname = document.getElementById("lname").value;
    var email = document.getElementById("email").value;
    var mobile = document.getElementById("mobile").value;
    var password = document.getElementById("password").value;
    var address = document.getElementById("address").value;




    const user_dto = {
        fname: fname,
        lname: lname,
        email: email,
        mobile: mobile,
        password: password,
        address: address,
    }


    const response = await fetch("Signup",
            {
                method: "POST",
                body: JSON.stringify(user_dto),
                headers: {
                    "Content-type": "applicaiton/json"
                }
            }
    );

    if (response.ok) {

        const json = await  response.json();

        if (json.success == true) {
            console.log(json.content);
            var content = json.content;
            // success
            popup.success({
                title: 'Success',
                message: content,
            });
           
            window.location = 'signin.html';
            
        } else {
            console.log(json.content);
            // error
            popup.error({
                title: 'Oops',
                message: json.content,
            });
        }
    } else {
        alert("Error");
    }

}