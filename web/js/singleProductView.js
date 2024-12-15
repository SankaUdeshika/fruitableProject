async function LoadSingleProduct() {

    const parameters = new URLSearchParams(window.location.search);
    if (parameters.has("id")) {
        const productID = parameters.get("id");

        const response = await fetch("LoadSingleProduct?id=" + productID);


        if (response.ok) {
            const jsonText = await response.json();
            console.log(jsonText.product.product_id);

            const product_id = jsonText.product.product_id;

            document.getElementById("product_name").innerHTML = jsonText.product.product_name;
            document.getElementById("product_category").innerHTML = jsonText.product.category.category_name;
            document.getElementById("product_price").innerHTML = "Rs." + jsonText.product.product_price;
            document.getElementById("description").innerHTML = jsonText.product.Description1;
            document.getElementById("add-to-cart-qty").max = jsonText.product.qty; // Set the maximum value
            document.getElementById("add-to-cart-qty").min = 1;

            document.getElementById("add-To-Cart").addEventListener("click",
                    (e) => {
                addToCart(productID,
                        document.getElementById("add-to-cart-qty").value
                        );
                e.preventDefault();
            })


            document.getElementById("image1").src = "product-images/" + product_id + "/image1.jpg";
            document.getElementById("image2").src = "product-images/" + product_id + "/image2.jpg";
            document.getElementById("image3").src = "product-images/" + product_id + "/image3.jpg";


        } else {
//            window.location = 'index.html';
        }

    } else {
//        window.location = 'index.html';
    }
}

async function addToCart(id, qty) {


    const response = await fetch(
            "AddToCart?id=" + id + "&qty=" + qty, {}
    );


    if (response.ok) {
        const data = await response.json();
        console.log(data);
        console.log("success");
    } else {
        console.log("error")
    }
}



//async function AddToCart() {
//
//    const cartparameters = new URLSearchParams(window.location.search);
//    const cartproductID = cartparameters.get("id");
//    var qty = document.getElementById("priceQty").value;
//
//    const Product_data = {
//        productId: cartproductID,
//        qty: qty,
//    }
//
//    const response = await fetch("AddToCart?pid=" + cartproductID + "&qty=" + qty, {
//        method: "POST",
//        body: JSON.stringify(Product_data),
//        headers: {
//            "Content-type": "applicaiton/json"
//        }
//    });
//
//
//    if (response.ok) {
//        alert("OK");
//    } else {
//        alert("NO");
//
//    }
//}

