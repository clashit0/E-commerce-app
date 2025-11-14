package com.abhinav.shoppingapp.data.repo

import android.net.Uri
import com.abhinav.shoppingapp.common.ADDTOFAV
import com.abhinav.shoppingapp.common.ADD_TO_CART
import com.abhinav.shoppingapp.common.PRODUCT_COLLECTION
import com.abhinav.shoppingapp.common.ResultState
import com.abhinav.shoppingapp.common.USER_COLLECTION
import com.abhinav.shoppingapp.domain.models.BannerDataModels
import com.abhinav.shoppingapp.domain.models.CartDataModels
import com.abhinav.shoppingapp.domain.models.CategoryDataModels
import com.abhinav.shoppingapp.domain.models.ProductsDataModels
import com.abhinav.shoppingapp.domain.models.UserData
import com.abhinav.shoppingapp.domain.models.UserDataParent
import com.abhinav.shoppingapp.domain.repo.Repo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class RepoImpl @Inject constructor(
    var firebaseAuth: FirebaseAuth,
    var firebaseFirestore: FirebaseFirestore,
) : Repo {
    override fun registerUserWithEmailAndPassword(userData: UserData): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)
            firebaseAuth.createUserWithEmailAndPassword(userData.email, userData.password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        firebaseFirestore.collection(USER_COLLECTION)
                            .document(it.result.user?.uid.toString()).set(userData)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    trySend(ResultState.Success("User Registered Successfully and add to Firestore"))
                                } else {
                                    if (it.exception != null) {
                                        trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                                    }
                                }
                            }
                    } else {
                        if (it.exception != null) {
                            trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                        }
                    }
                }
            awaitClose {
                close()
            }

        }


    override fun loginUserWithEmailAndPassword(userData: UserData): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)
            firebaseAuth.signInWithEmailAndPassword(userData.email, userData.password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        trySend(ResultState.Success("User Logged In Successfully"))

                    } else {
                        if (it.exception != null) {
                            trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                        }

                    }
                }
            awaitClose {
                close()
            }
        }

    override fun getUserById(uid: String): Flow<ResultState<UserDataParent>> =
        callbackFlow {
            trySend(ResultState.Loading)

            firebaseFirestore.collection(USER_COLLECTION).document(uid).get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val data = it.result.toObject(UserData::class.java) ?: UserData()
                        val userDataParent = UserDataParent(it.result.id, userData = data)
                        trySend(ResultState.Success(userDataParent))

                    } else {
                        if (it.exception != null) {
                            trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                        }

                    }
                }
            awaitClose {
                close()
            }
        }

    override fun updateUserData(userDataParent: UserDataParent): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)
            firebaseFirestore.collection(USER_COLLECTION).document(userDataParent.nodeId)
                .update(userDataParent.userData.toMap()).addOnCompleteListener {
                    if (it.isSuccessful) {
                        trySend(ResultState.Success("User Data Updated Successfully"))
                    } else {
                        if (it.exception != null) {
                            trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                        }
                    }
                }
            awaitClose {
                close()
            }


        }

    override fun userProfileImage(uri: Uri): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)
            FirebaseStorage.getInstance().reference.child("userProfileImage/${System.currentTimeMillis()}+${firebaseAuth.currentUser?.uid}")
                .putFile(
                    uri
                ).addOnCompleteListener {
                    it.result.storage.downloadUrl.addOnSuccessListener { imageUri ->
                        trySend(ResultState.Success(imageUri.toString()))
                    }
                    if (it.exception != null) {
                        trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                    }
                }
            awaitClose {
                close()
            }

        }

    override fun getCategoriesInLimited(): Flow<ResultState<List<CategoryDataModels>>> =
        callbackFlow {
            trySend(ResultState.Loading)
            firebaseFirestore.collection("categories").limit(7).get()
                .addOnSuccessListener { querySnapshot ->
                    val categories = querySnapshot.mapNotNull { document ->
                        document.toObject(CategoryDataModels::class.java)

                    }
                    trySend(ResultState.Success(categories))
                }.addOnFailureListener { exception ->
                    trySend(ResultState.Error(exception.toString()))
                }
            awaitClose {
                close()
            }
        }


    override fun getProductsInLimited(): Flow<ResultState<List<ProductsDataModels>>> =
        callbackFlow {
            trySend(ResultState.Loading)
            firebaseFirestore.collection("Products").limit(10).get().addOnSuccessListener {
                val products = it.documents.mapNotNull { document ->
                    document.toObject(ProductsDataModels::class.java)?.apply {
                        productId = document.id
                    }
                }
                trySend(ResultState.Success(products))

            }.addOnFailureListener { exception ->
                trySend(ResultState.Error(exception.toString()))
            }
            awaitClose {
                close()
            }
        }

    override fun getAllProducts(): Flow<ResultState<List<ProductsDataModels>>> =
        callbackFlow {
            trySend(ResultState.Loading)
            firebaseFirestore.collection("Products").get().addOnSuccessListener {
                val products = it.documents.mapNotNull {
                    it.toObject(ProductsDataModels::class.java)?.apply {
                        productId = it.id
                    }
                }
                trySend(ResultState.Success(products))
            }.addOnFailureListener {
                trySend(ResultState.Error(it.toString()))
            }
            awaitClose {
                close()
            }

        }

    override fun getProductById(productId: String): Flow<ResultState<ProductsDataModels>> =
        callbackFlow {
            trySend(ResultState.Loading)
            firebaseFirestore.collection(PRODUCT_COLLECTION).document(productId).get()
                .addOnSuccessListener {
                    val product = it.toObject(ProductsDataModels::class.java)
                    trySend(ResultState.Success(product!!))
                }.addOnFailureListener {
                    trySend(ResultState.Error(it.toString()))
                }
            awaitClose {
                close()
            }
        }

    override fun addToCart(cartDataModels: CartDataModels): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)

            val userId = firebaseAuth.currentUser!!.uid
            val cartRef = firebaseFirestore.collection(ADD_TO_CART)
                .document(userId)
                .collection("User_Cart")

            // Check if the same product already exists in cart
            cartRef
                .whereEqualTo("productId", cartDataModels.productId)
                .whereEqualTo("size", cartDataModels.size)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    if (querySnapshot.isEmpty) {
                        // Product NOT in cart - ADD NEW item
                        cartRef.add(cartDataModels).addOnSuccessListener {
                            trySend(ResultState.Success("Product added to cart"))
                        }.addOnFailureListener {
                            trySend(ResultState.Error(it.toString()))
                        }
                    } else {
                        // Product ALREADY in cart - UPDATE QUANTITY
                        val existingItem = querySnapshot.documents.first()
                        val currentQty = existingItem.getLong("quantity")?.toInt() ?: 0 // Now using getLong for Int
                        val newQty = currentQty + cartDataModels.quantity // Simple addition with Int

                        existingItem.reference.update("quantity", newQty)
                            .addOnSuccessListener {
                                trySend(ResultState.Success("Cart quantity updated to $newQty"))
                            }.addOnFailureListener {
                                trySend(ResultState.Error(it.toString()))
                            }
                    }
                }.addOnFailureListener {
                    trySend(ResultState.Error(it.toString()))
                }
            awaitClose {
                close()
            }
        }

    override fun addToFav(productsDataModels: ProductsDataModels): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)

            val userId = firebaseAuth.currentUser!!.uid
            val favDocRef = firebaseFirestore.collection(ADDTOFAV)
                .document(userId)
                .collection("User_Fav")
                .document(productsDataModels.productId) // Use productId as document ID

            // Check if already exists (get() is faster than query)
            favDocRef.get().addOnSuccessListener { document ->
                if (document.exists()) {
                    // EXISTS - REMOVE from wishlist (toggle off)
                    favDocRef.delete().addOnSuccessListener {
                        trySend(ResultState.Success("Product removed from wishlist"))
                    }.addOnFailureListener {
                        trySend(ResultState.Error(it.toString()))
                    }
                } else {
                    // NOT EXISTS - ADD to wishlist (toggle on)
                    favDocRef.set(productsDataModels).addOnSuccessListener {
                        trySend(ResultState.Success("Product added to wishlist"))
                    }.addOnFailureListener {
                        trySend(ResultState.Error(it.toString()))
                    }
                }
            }.addOnFailureListener {
                trySend(ResultState.Error(it.toString()))
            }
            awaitClose {
                close()
            }
        }

    override fun getAllFav(): Flow<ResultState<List<ProductsDataModels>>> =
        callbackFlow {
            trySend(ResultState.Loading)
            firebaseFirestore.collection(ADDTOFAV).document(firebaseAuth.currentUser!!.uid)
                .collection("User_Fav").get().addOnSuccessListener {
                    val fav = it.documents.mapNotNull { documentSnapshot ->
                        documentSnapshot.toObject(ProductsDataModels::class.java)
                    }
                    trySend(ResultState.Success(fav))
                }.addOnFailureListener {
                    trySend(ResultState.Error(it.toString()))
                }
            awaitClose {
                close()
            }
        }

    override fun getCart(): Flow<ResultState<List<CartDataModels>>> =
        callbackFlow {
            trySend(ResultState.Loading)
            firebaseFirestore.collection(ADD_TO_CART).document(firebaseAuth.currentUser!!.uid)
                .collection("User_Cart").get().addOnSuccessListener {
                    val cart = it.documents.mapNotNull { documentSnapshot ->
                        documentSnapshot.toObject(CartDataModels::class.java)?.apply {
                            cartId = documentSnapshot.id
                        }
                    }
                    trySend(ResultState.Success(cart))
                }.addOnFailureListener {
                    trySend(ResultState.Error(it.toString()))
                }
            awaitClose {
                close()
            }
        }

    override fun getAllCategories(): Flow<ResultState<List<CategoryDataModels>>> =
        callbackFlow {
            trySend(ResultState.Loading)
            firebaseFirestore.collection("categories").get().addOnSuccessListener {
                val categories = it.documents.mapNotNull { documentSnapshot ->
                    documentSnapshot.toObject(CategoryDataModels::class.java)
                }
                trySend(ResultState.Success(categories))
            }.addOnFailureListener {
                trySend(ResultState.Error(it.toString()))
            }
            awaitClose {
                close()
            }
        }

    override fun getCheckout(productId: String): Flow<ResultState<ProductsDataModels>> =
        callbackFlow {
            trySend(ResultState.Loading)
            firebaseFirestore.collection(PRODUCT_COLLECTION).document(productId).get()
                .addOnSuccessListener {
                    val product = it.toObject(ProductsDataModels::class.java)
                    trySend(ResultState.Success(product!!))
                }.addOnFailureListener {
                    trySend(ResultState.Error(it.toString()))
                }
            awaitClose {
                close()
            }
        }

    override fun getBanner(): Flow<ResultState<List<BannerDataModels>>> =
        callbackFlow {
            trySend(ResultState.Loading)
            firebaseFirestore.collection("banner").get().addOnSuccessListener {
                val banner = it.documents.mapNotNull { documentSnapshot ->

                    documentSnapshot.toObject(BannerDataModels::class.java)
                }

                trySend(ResultState.Success(banner))
            }.addOnFailureListener {
                trySend(ResultState.Error(it.toString()))
            }
            awaitClose {
                close()
            }
        }

    override fun getSpecificCategoryItems(categoryName: String): Flow<ResultState<List<ProductsDataModels>>> =
        callbackFlow {
            trySend(ResultState.Loading)
            firebaseFirestore.collection("Products").whereEqualTo("category", categoryName)
                .get().addOnSuccessListener {
                    val product = it.documents.mapNotNull {
                        it.toObject(ProductsDataModels::class.java)?.apply {
                            productId = it.id
                        }
                    }
                    trySend(ResultState.Success(product))
                }.addOnFailureListener {
                    trySend(ResultState.Error(it.toString()))
                }
            awaitClose {
                close()
            }
        }

    override fun getAllSuggestedProducts(): Flow<ResultState<List<ProductsDataModels>>> =
        callbackFlow {
            trySend(ResultState.Loading)
            firebaseFirestore.collection(ADDTOFAV).document(firebaseAuth.currentUser!!.uid)
                .collection("User_Fav").get().addOnSuccessListener {
                    val fav = it.documents.mapNotNull { documentSnapshot ->
                        documentSnapshot.toObject(ProductsDataModels::class.java)?.apply {
                            productId =
                                documentSnapshot.id //added by my self leaving for later review

                        }
                    }
                    trySend(ResultState.Success(fav))
                }.addOnFailureListener {
                    trySend(ResultState.Error(it.toString()))
                }
            awaitClose {
                close()
            }
        }

}