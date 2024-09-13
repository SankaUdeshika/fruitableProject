
async  function signin() {
    const popup = Notification({
        position: 'bottom-right',
        duration: 4000,
        isHidePrev: false,
        isHideTitle: false,
        maxOpened: 3,
    });
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
    const user_dto = {
        email: email,
        password: password,
    }
    const response = await fetch("Signin", {
        method: "POST",
        body: JSON.stringify(user_dto),
        headers: {
            "Contetn-Type": "application/json"
        }
    }
    );
    if (response.ok) {
        const jsonText = await  response.json();



        if (jsonText.success) {
//            Redirect Page home
            // success
            popup.success({
                title: 'Success',
                message: content,
            });
            window.location = "index.html";
        } else {

            if (jsonText.content == "unverified") {

                document.getElementById("verificationItem").style.display = 'block';
                // error
                popup.error({
                    title: 'Not Verified',
                    message: "Please Check you Mails and Enter Verificaiton code",
                });
            } else {
                // error
                popup.error({
                    title: 'Oops',
                    message: jsonText.content,
                });
            }
        }


    } else {
        alert("Other Error");
        // error
        popup.error({
            title: 'Oops',
            message: jsonText.content,
        });
    }


}

