package com.mlpopularmvs.movietrailer;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class PurchaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setTitle("Purchase");

        setSupportActionBar(toolbar);

        Button btn_sups = findViewById(R.id.btn_sups);
        Button btn_sups2 = findViewById(R.id.btn_sups2);
        Button btn_purchase = findViewById(R.id.btn_purchase);
        Button btn_purchase1 = findViewById(R.id.btn_purchase1);
        Button btn_purchase2 = findViewById(R.id.btn_purchase2);
        Button btn_purchase3 = findViewById(R.id.btn_purchase3);
        Button btn_purchase4 = findViewById(R.id.btn_purchase4);


        btn_sups.setText(PurchaseUtils.getSharedInstance().getPriceSub(PurchaseUtils.purchase_subs_1));
        btn_sups2.setText(PurchaseUtils.getSharedInstance().getPriceSub(PurchaseUtils.purchase_subs_2));
        btn_purchase.setText(PurchaseUtils.getSharedInstance().getPrice(PurchaseUtils.purchase_premium));
        btn_purchase1.setText(PurchaseUtils.getSharedInstance().getPrice(PurchaseUtils.purchase_premium1));
        btn_purchase2.setText(PurchaseUtils.getSharedInstance().getPrice(PurchaseUtils.purchase_premium2));
        btn_purchase3.setText(PurchaseUtils.getSharedInstance().getPrice(PurchaseUtils.purchase_premium3));
        btn_purchase4.setText(PurchaseUtils.getSharedInstance().getPrice(PurchaseUtils.purchase_premium4));

        btn_sups.setOnClickListener(v -> {
            PurchaseUtils.getSharedInstance().purchaseSub1(PurchaseActivity.this, new PurchaseListener() {
                @Override
                public void purchaseFailed(String item) {
                    Toast.makeText(v.getContext(), "failed", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void purchaseSuccess(String item) {

                    Toast.makeText(v.getContext(), "success", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void purchaseCancel(String item) {

                }
            });
        });

        btn_sups2.setOnClickListener(v -> {
            PurchaseUtils.getSharedInstance().purchaseSub2(PurchaseActivity.this, new PurchaseListener() {
                @Override
                public void purchaseFailed(String item) {
                    Toast.makeText(v.getContext(), "failed", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void purchaseSuccess(String item) {

                    Toast.makeText(v.getContext(), "success", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void purchaseCancel(String item) {

                }
            });
        });

        btn_purchase.setOnClickListener(v -> {
            PurchaseUtils.getSharedInstance().purchasePremium(PurchaseActivity.this, new PurchaseListener() {
                @Override
                public void purchaseFailed(String item) {
                    Toast.makeText(v.getContext(), "failed", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void purchaseSuccess(String item) {

                    Toast.makeText(v.getContext(), "success", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void purchaseCancel(String item) {

                }
            });
        });

        btn_purchase1.setOnClickListener(v -> {
            PurchaseUtils.getSharedInstance().purchasePremium1(PurchaseActivity.this, new PurchaseListener() {
                @Override
                public void purchaseFailed(String item) {
                    Toast.makeText(v.getContext(), "failed", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void purchaseSuccess(String item) {

                    Toast.makeText(v.getContext(), "success", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void purchaseCancel(String item) {

                }
            });
        });

        btn_purchase2.setOnClickListener(v -> {
            PurchaseUtils.getSharedInstance().purchasePremium2(PurchaseActivity.this, new PurchaseListener() {
                @Override
                public void purchaseFailed(String item) {
                    Toast.makeText(v.getContext(), "failed", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void purchaseSuccess(String item) {

                    Toast.makeText(v.getContext(), "success", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void purchaseCancel(String item) {

                }
            });
        });

        btn_purchase3.setOnClickListener(v -> {
            PurchaseUtils.getSharedInstance().purchasePremium3(PurchaseActivity.this, new PurchaseListener() {
                @Override
                public void purchaseFailed(String item) {
                    Toast.makeText(v.getContext(), "failed", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void purchaseSuccess(String item) {

                    Toast.makeText(v.getContext(), "success", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void purchaseCancel(String item) {

                }
            });
        });

        btn_purchase4.setOnClickListener(v -> {
            PurchaseUtils.getSharedInstance().purchasePremium4(PurchaseActivity.this, new PurchaseListener() {
                @Override
                public void purchaseFailed(String item) {
                    Toast.makeText(v.getContext(), "failed", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void purchaseSuccess(String item) {

                    Toast.makeText(v.getContext(), "success", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void purchaseCancel(String item) {

                }
            });
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}