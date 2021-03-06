package com.boardinglabs.mireta.standalone.modul.transactions.items;

import com.boardinglabs.mireta.standalone.component.network.ResponeError;
import com.boardinglabs.mireta.standalone.component.network.entities.TransactionPost;
import com.boardinglabs.mireta.standalone.component.network.response.ItemsResponse;
import com.boardinglabs.mireta.standalone.modul.CommonInterface;

import okhttp3.ResponseBody;
import rx.Subscriber;

public class ItemsPresenter {
    private CommonInterface cInterface;
    private ItemsView mView;
    private ItemsInteractor mInteractor;

    public ItemsPresenter(CommonInterface cInterface, ItemsView view) {
        mView = view;
        this.cInterface = cInterface;
        mInteractor = new ItemsInteractor(this.cInterface.getService());
    }

    public void stockIteHms(String businessId) {
//        cInterface.showProgressLoading();

        mInteractor.getStockItems(businessId).subscribe(new Subscriber<ItemsResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
//                cInterface.hideProgresLoading();
                cInterface.onFailureRequest(ResponeError.getErrorMessage(e));
            }

            @Override
            public void onNext(ItemsResponse itemsResponse) {
//                cInterface.hideProgresLoading();
                mView.onSuccessGetItems(itemsResponse.data);
            }
        });
    }

    public void createTransaction(TransactionPost transactionPost, String token) {
        cInterface.showProgressLoading();

        mInteractor.createTransaction(transactionPost, token).subscribe(new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                cInterface.hideProgresLoading();
                cInterface.onFailureRequest(ResponeError.getErrorMessage(e));
            }

            @Override
            public void onNext(ResponseBody responseBody) {

                cInterface.hideProgresLoading();
                mView.onSuccessCreateTransaction(responseBody);
            }
        });
    }
}
