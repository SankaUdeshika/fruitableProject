async function LoadSingleProduct() {

    const parameters = new URLSearchParams(window.location.search);

    if (parameters.has("id")) {
        const productID = parameters.get("id");

        const response = await fetch("LoadSingleProduct?id=" + productID);


        if (response.ok) {
            const jsonText = await response.json();
//            console.log(jsonText.product.product_id);

            const product_id = jsonText.product.product_id;

            document.getElementById("product_name").innerHTML = jsonText.product.product_name;
            document.getElementById("product_category").innerHTML = jsonText.product.category.category_name;
            document.getElementById("product_price").innerHTML = "Rs." + jsonText.product.product_price;
            document.getElementById("description").innerHTML = jsonText.product.Description1;
            document.getElementById("priceQty").max = jsonText.product.qty;
            document.getElementById("priceQty").min = 1;


            document.getElementById("image1").src = "product-images/"+product_id+"/image1.jpg";
            document.getElementById("image2").src = "product-images/"+product_id+"/image2.jpg";
            document.getElementById("image3").src = "product-images/"+product_id+"/image3.jpg";


        } else {
//            window.location = 'index.html';
        }

    } else {
//        window.location = 'index.html';
    }


}


