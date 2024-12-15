async function  loadCart() {


//    const popup = Notification({
//        position: 'bottom-right',
//        duration: 4000,
//        isHidePrev: false,
//        isHideTitle: false,
//        maxOpened: 3,
//    });


    const response = await fetch("LoadCartDetails");
    if (response.ok) {
        let responseText = await response.json();
        const productList = responseText.cartList;
        if (responseText.response_dto == '{"success":false,"content":"login"}') {
            window.location = 'signin.html';
        } else if (responseText.response_dto == '{"success":true,"content":"success"}') {




            let total = 0;

            let productHtml = document.getElementById("cartCard");
            productList.forEach(products => {
                let productClone = productHtml.cloneNode(true);
                productClone.querySelector("#product-images").src = "product-images/" + products.product_product_id.product_id + "/image1.jpg";
                productClone.querySelector("#product-name").innerHTML = products.product_product_id.product_name;
                productClone.querySelector("#product-price").innerHTML = "Rs." + products.product_product_id.product_price + " /kg";
                productClone.querySelector("#add-to-cart-qty").value = products.qty;

                total = total + products.qty * products.product_product_id.product_price;



//                document.getElementById("add-to-cart-qty").max = products.qty; // Set the maximum value
//                document.getElementById("add-to-cart-qty").min = 1;
//
                document.getElementById("add-To-Cart").addEventListener("click",
                        (e) => {
                    addToCart(productID,
                            document.getElementById("add-to-cart-qty").value
                            );
                    e.preventDefault();
                })
                document.getElementById("productBox").appendChild(productClone);
//
            });
            alert(total);
            document.getElementById("totalPrice").innerHTML = "Rs." + total;
            document.getElementById("subTotal").innerHTML = "Rs." + total;

            productHtml.style.display = "none";

        }
    } else {
        alert("something wrong");
    }


}


