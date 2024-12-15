async function loadCheckout() {
    const response = await fetch("loadCheckoutDetails");
    if (response.ok) {
        let responseText = await response.json();
        const productList = responseText.cartList;

        const userfname = responseText.userfname;
        const userlname = responseText.userlname;
        const usermobile = responseText.usermobile;
        const useraddress = responseText.useraddress;
        const userEmail = responseText.userEmail;



        if (responseText.response_dto == '{"success":false,"content":"login"}') {
            window.location = 'signin.html';
        } else if (responseText.response_dto == '{"success":true,"content":"success"}') {


            let total = 0;
            let productHtml = document.getElementById("productcard");
            productList.forEach(products => {
                let productClone = productHtml.cloneNode(true);
                productClone.querySelector("#product-images").src = "product-images/" + products.product_product_id.product_id + "/image1.jpg";
                productClone.querySelector("#product-name").innerHTML = products.product_product_id.product_name;
                productClone.querySelector("#product-price").innerHTML = "Rs." + products.product_product_id.product_price + " /kg";
                productClone.querySelector("#add-to-cart-qty").value = products.qty;
                total = total + products.qty * products.product_product_id.product_price;
                document.getElementById("productBox").appendChild(productClone);
            });
            document.getElementById("totalPrice").innerHTML = "Rs." + total;
            productHtml.style.display = "none";


//              get user Details
            document.getElementById("fname").value = userfname;
            document.getElementById("lname").value = userlname;
            document.getElementById("address").value = useraddress;
            document.getElementById("mobile").value = usermobile;
            document.getElementById("email").value = userEmail;





        }

    } else {
        alert("Something Wrong Please try again later");
    }
}