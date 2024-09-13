async function signup() {
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
            alert(json.content);
            console.log(json.content);
        } else {
            console.log(json.content);
        }
    } else {
        alert("Error");
    }

}