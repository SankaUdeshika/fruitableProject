
async function  LoadCategory() {

    const response = await fetch("LoadMyAccountPropperties");


    if (response.ok) {


        const jsonText = await  response.json();
        const CategoryList = jsonText.categoryList;

//        add Category Selector

        const SelectTag = document.getElementById("category");
        CategoryList.forEach(category => {
            let optionTag = document.createElement("option");
            optionTag.value = category.category_id;
            optionTag.innerHTML = category.category_name;
            SelectTag.appendChild(optionTag);
        });


    }

}











async function  AddingProduct() {
    const popup = Notification({
        position: 'bottom-right',
        duration: 4000,
        isHidePrev: false,
        isHideTitle: false,
        maxOpened: 3,
    });

    var productName = document.getElementById("productName").value;
    var productPrice = document.getElementById("productPrice").value;
    var productQty = document.getElementById("productQty").value;
    var category = document.getElementById("category").value;
    var productDescription = document.getElementById("productDescription").value;



    var image1 = document.getElementById("image1").files[0];
    var image2 = document.getElementById("image2").files[0];
    var image3 = document.getElementById("image3").files[0];


    const data = new FormData();

    data.append("product_name", productName);

    data.append("product_price", productPrice);

    data.append("qty", productQty);
    data.append("Category_category_id", category);
    data.append("description", productDescription);

    data.append("image1", image1);
    data.append("image2", image2);
    data.append("image3", image3);


    const response = await fetch("ProductAdding", {method: "POST", body: data});


    if (response.ok) {
        const jsonResponse = await  response.json();

        if (jsonResponse.success) {
            popup.success({
                title: 'Success',
                message: jsonResponse.content,
            }); 
        } else {
            console.log(jsonResponse.content);
            popup.error({
                title: 'Errorsss',
                message: jsonResponse.content,
            });
        }

        // success

    } else {
        const jsonResponse = await  response.json();

        // error
        popup.error({
            title: 'Errorsss',
            message: jsonResponse.content,
        });
    }

}




