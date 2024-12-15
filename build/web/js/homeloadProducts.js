async function HomeLoadProdcuts(categoryType) {
    var type = categoryType;
    const popup = Notification({
        position: 'bottom-right',
        duration: 4000,
        isHidePrev: false,
        isHideTitle: false,
        maxOpened: 3,
    });
    var response = "";
    if (type == "All") {
        response = await fetch("LoadHomeProduts?type=" + type);
    } else if (type == "veg") {
        response = await fetch("LoadHomeProduts?type=" + type);
    } else if (type == "fruit") {
        response = await fetch("LoadHomeProduts?type=" + type);
    } else if (type == "bread") {
        response = await fetch("LoadHomeProduts?type=" + type);
    } else if (type == "meat") {
        response = await fetch("LoadHomeProduts?type=" + type);
    }

////    get response
    if (response.ok) {
        const responseText = await response.json();
        const productList = responseText.productList;
        const dto = responseText.response_Dto;
        if (dto == '{"success":true,"content":"Success"}') {
            popup.success({
                title: 'Product Loaded',
                message: "All Product Loaded",
            });
            let productHtml = document.getElementById("productCard");
            productList.forEach(products => {

                let productClone = productHtml.cloneNode(true);
                console.log(products.product_name);
                productClone.querySelector("#category").innerHTML = products.category.category_name;
                productClone.querySelector("#product-images").src = "product-images/" + products.product_id + "/image1.jpg";
                productClone.querySelector("#product-name").innerHTML = products.product_name;
                productClone.querySelector("#product-description").innerHTML = products.Description1;
                productClone.querySelector("#product-price").innerHTML = "Rs." + products.product_price + " /kg";
                productClone.querySelector("#singleViewLink").addEventListener("click", function () {
                    window.location = 'SingleProductView.html?pid=' + products.product_id;
                });


                document.getElementById("productBox").appendChild(productClone);

            });
            productHtml.style.display = "none";

        } else {
            popup.error({
                title: 'Empty Products',
                message: "you have nothing to show products in this category",
            });
        }

    } else {
        alert("not working");
        popup.error({
            title: 'Error',
            message: "OK",
        });
    }


}

