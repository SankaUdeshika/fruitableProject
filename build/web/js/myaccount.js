
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











//async function  AddingProduct() {
//
//    var productName = document.getElementById("productName").value;
//    var productPrice = document.getElementById("productPrice").value;
//    var productQty = document.getElementById("productQty").value;
//    var category = document.getElementById("category").value;
//    var productDescription = document.getElementById("productDescription").value;
//
//    var image1 = document.getElementById("image1").files[0];
//    var image2 = document.getElementById("image2").files[0];
//    var image3 = document.getElementById("image3").files[0];
//
//
//    const data = new formData();
//    data.append("product_name", productName);
//    data.append("product_price", productName);
//    data.append("qty", productName);
//    data.append("Category_category_id", productName);
//    data.append("description", productName);
////    data.append("user_email", productName);
//}




