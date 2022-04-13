package com.mlpopularmvs.movietrailer;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;

import java.util.Arrays;
import java.util.List;

public class PurchaseUtils implements PurchasesUpdatedListener {
    private static PurchaseUtils sharedInstance;

    public static final String purchase_remove_ads = "remove_ads";
    public static final String purchase_premium = "premium";
    public static final String purchase_premium1 = "premium1";
    public static final String purchase_premium2 = "premium2";
    public static final String purchase_premium3 = "premium3";
    public static final String purchase_premium4 = "premium4";
    public static final String purchase_subs_1 = "subs_1";
    public static final String purchase_subs_2 = "subs_2";

    private BillingClient billingClient;
    private boolean canPurchase = false;
    private List<String> sku_list = Arrays.asList(purchase_remove_ads, purchase_premium, purchase_premium1, purchase_premium2, purchase_premium3, purchase_premium4);
    private List<String> sku_list_subs = Arrays.asList(purchase_subs_1, purchase_subs_2);

    private String currentItem;
    private List<SkuDetails> skuDetailsList;
    private List<SkuDetails> skuDetailsList_sub;
    private PurchaseListener purchaseListener;
    private Context current_context;


    public static PurchaseUtils getSharedInstance() {
        if (sharedInstance == null) {
            sharedInstance = new PurchaseUtils();
        }
        return sharedInstance;
    }

    public String getPrice(String item) {
        if (skuDetailsList == null || skuDetailsList.size() == 0)
            return "Purchase";
        else {
            for (SkuDetails skuDetail : skuDetailsList) {
                if (item.equals(skuDetail.getSku()))
                    return skuDetail.getPrice();
            }
        }
        return "Purchase";
    }

    public String getPriceSub(String item) {
        if (skuDetailsList_sub == null || skuDetailsList_sub.size() == 0)
            return "Purchase";
        else {
            for (SkuDetails skuDetail : skuDetailsList_sub) {
                if (item.equals(skuDetail.getSku()))
                    return skuDetail.getPrice();
            }
        }
        return "Purchase";
    }

    public void init(Context context) {
        billingClient = BillingClient.newBuilder(context).enablePendingPurchases().setListener(this).build();
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.
                    canPurchase = true;
//                    Utils.logDebug(this.getClass(), "Billing connected");
                    querySkuLists();
                    querySkuListsSub();
                    queryPurchase(context);
                    queryPurchaseSub(context);
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
//                Utils.logDebug(this.getClass(), "Billing Disconnected");
            }
        });
    }

    private void querySkuLists() {
        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
        params.setSkusList(sku_list).setType(BillingClient.SkuType.INAPP);
        billingClient.querySkuDetailsAsync(params.build(),
                (billingResult1, skuDetailsList) -> {
                    if (billingResult1.getResponseCode() == BillingClient.BillingResponseCode.OK
                            && skuDetailsList != null) {

                        this.skuDetailsList = skuDetailsList;
                    } else {
//                        Utils.logDebug(this.getClass(), "Purchase failed to query sku list");
                    }
                });
    }

    private void querySkuListsSub() {
        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
        params.setSkusList(sku_list_subs).setType(BillingClient.SkuType.SUBS);
        billingClient.querySkuDetailsAsync(params.build(),
                (billingResult1, skuDetailsList) -> {
                    if (billingResult1.getResponseCode() == BillingClient.BillingResponseCode.OK
                            && skuDetailsList != null) {

                        this.skuDetailsList_sub = skuDetailsList;
                    } else {
//                        Utils.logDebug(this.getClass(), "Purchase failed to query sku list");
                    }
                });
    }

    private void queryPurchase(Context context) {
        billingClient.queryPurchasesAsync(BillingClient.SkuType.INAPP, (billingResult, list) ->
        {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list != null) {
                for (Purchase purchase : list) {
                    if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
                        if (purchase.getSkus() != null && purchase.getSkus().size() > 0) {
                            SharedPreferences mPrefs = context.getSharedPreferences("MOVIETRAILER_APP", Context.MODE_PRIVATE);
                            mPrefs.edit().putInt(purchase.getSkus().get(0), 1).apply();
                            SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                            defaultSharedPreferences.edit().putInt(purchase.getSkus().get(0), 1).apply();
                        }

//                        Utils.logDebug(this.getClass(), "Continous purchase successful");
                        ConsumeParams consumeParams =
                                ConsumeParams.newBuilder()
                                        .setPurchaseToken(purchase.getPurchaseToken())
                                        .build();
                        billingClient.consumeAsync(consumeParams, (result, purchaseToken) -> {
                            if (result.getResponseCode() == BillingClient.BillingResponseCode.OK) {
//                                Utils.logDebug(this.getClass(), "consume OK");
                            }
                        });
                    }
                }
            }
        });
    }

    private void queryPurchaseSub(Context context) {
        billingClient.queryPurchasesAsync(BillingClient.SkuType.SUBS, (billingResult, list) ->
        {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list != null) {
                for (Purchase purchase : list) {
                    if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
                        if (purchase.getSkus() != null && purchase.getSkus().size() > 0) {
                            SharedPreferences mPrefs = context.getSharedPreferences("MOVIETRAILER_APP", Context.MODE_PRIVATE);
                            mPrefs.edit().putInt(purchase.getSkus().get(0), 1).apply();
                            SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                            defaultSharedPreferences.edit().putInt(purchase.getSkus().get(0), 1).apply();
                        }

//                        Utils.logDebug(this.getClass(), "Continous purchase successful");
                        ConsumeParams consumeParams =
                                ConsumeParams.newBuilder()
                                        .setPurchaseToken(purchase.getPurchaseToken())
                                        .build();
                        billingClient.consumeAsync(consumeParams, (result, purchaseToken) -> {
                            if (result.getResponseCode() == BillingClient.BillingResponseCode.OK) {
//                                Utils.logDebug(this.getClass(), "consume OK");
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void onPurchasesUpdated(BillingResult billingResult, @Nullable List<Purchase> purchases) {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK
                && purchases != null) {
            for (Purchase purchase : purchases) {
                handlePurchase(purchase);
            }
        } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
//            Utils.logDebug(this.getClass(), "purchase cancel");

            if (purchaseListener != null)
                purchaseListener.purchaseCancel(currentItem);
        } else {
//            Utils.logDebug(this.getClass(), "purchase others error");
            if (purchaseListener != null)
                purchaseListener.purchaseFailed(currentItem);
        }
    }

    public void purchasePremium(Activity context, PurchaseListener listener) {
        purchaseItem(context, purchase_premium, listener);
    }

    public void purchasePremium1(Activity context, PurchaseListener listener) {
        purchaseItem(context, purchase_premium1, listener);
    }

    public void purchasePremium2(Activity context, PurchaseListener listener) {
        purchaseItem(context, purchase_premium2, listener);
    }

    public void purchasePremium3(Activity context, PurchaseListener listener) {
        purchaseItem(context, purchase_premium3, listener);
    }

    public void purchasePremium4(Activity context, PurchaseListener listener) {
        purchaseItem(context, purchase_premium4, listener);
    }

    public void purchaseRemoveAds(Activity context, PurchaseListener listener) {
        purchaseItem(context, purchase_remove_ads, listener);
    }

    public void purchaseSub1(Activity context, PurchaseListener listener) {
        purchaseItemSub(context, "subs_1", listener);
    }

    public void purchaseSub2(Activity context, PurchaseListener listener) {
        purchaseItemSub(context, "subs_2", listener);
    }


    private void purchaseItem(Activity context, String item, PurchaseListener listener) {
//        if (BuildConfig.DEBUG) {
//            this.purchaseListener = listener;
//            purchaseSuccessItem(context, item);
//        } else {
        if (canPurchase && skuDetailsList != null) {
            for (SkuDetails skuDetail : skuDetailsList) {
                if (item.equals(skuDetail.getSku())) {
                    currentItem = item;
                    current_context = context;
                    this.purchaseListener = listener;

                    BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                            .setSkuDetails(skuDetail)
                            .build();
                    billingClient.launchBillingFlow(context, flowParams);
//                        Utils.logDebug(this.getClass(), "purchase show dialog");
                    return;
                }
            }
            if (listener != null)
                listener.purchaseFailed(item);
        } else {
            if (listener != null)
                listener.purchaseFailed(item);
        }
//        }
    }

    private void purchaseItemSub(Activity context, String item, PurchaseListener listener) {
//        if (BuildConfig.DEBUG) {
//            this.purchaseListener = listener;
//            purchaseSuccessItem(context, item);
//        } else {
        if (canPurchase && skuDetailsList_sub != null) {
            for (SkuDetails skuDetail : skuDetailsList_sub) {
                if (item.equals(skuDetail.getSku())) {
                    currentItem = item;
                    current_context = context;
                    this.purchaseListener = listener;

                    BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                            .setSkuDetails(skuDetail)
                            .build();
                    billingClient.launchBillingFlow(context, flowParams);
//                        Utils.logDebug(this.getClass(), "purchase show dialog");
                    return;
                }
            }
            if (listener != null)
                listener.purchaseFailed(item);
        } else {
            if (listener != null)
                listener.purchaseFailed(item);
        }
//        }
    }

    private void handlePurchase(Purchase purchase) {
        if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
            purchaseSuccessItem(current_context, currentItem);
//            Utils.logDebug(this.getClass(), "purchase successful");
            ConsumeParams consumeParams =
                    ConsumeParams.newBuilder()
                            .setPurchaseToken(purchase.getPurchaseToken())
                            .build();
            billingClient.consumeAsync(consumeParams, (billingResult, purchaseToken) -> {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
//                    Utils.logDebug(this.getClass(), "consume OK");
                }
            });
        } else {
            if (purchaseListener != null)
                purchaseListener.purchaseFailed(currentItem);
        }
    }

    private void purchaseSuccessItem(Context context, String item) {
        if (context != null) {
            SharedPreferences mPrefs = context.getSharedPreferences("MOVIETRAILER_APP", Context.MODE_PRIVATE);
            mPrefs.edit().putInt(item, 1).apply();
            SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            defaultSharedPreferences.edit().putInt(item, 1).apply();
        }
        if (purchaseListener != null)
            purchaseListener.purchaseSuccess(currentItem);
    }
}
