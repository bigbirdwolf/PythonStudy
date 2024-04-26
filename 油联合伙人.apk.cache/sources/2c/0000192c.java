package com.yltx.oil.partner.modules.oiltrade.domain;

import com.yltx.oil.partner.data.network.HttpResult;
import com.yltx.oil.partner.data.repository.Repository;
import com.yltx.oil.partner.data.response.GiftCardResp;
import com.yltx.oil.partner.mvp.domain.UseCase;
import javax.inject.Inject;
import rx.Observable;

/* loaded from: classes.dex */
public class GiftCardDetailUseCase extends UseCase<HttpResult<GiftCardResp>> {
    private Repository mRepository;
    private String rowId;

    public String getRowId() {
        return this.rowId;
    }

    public void setRowId(String str) {
        this.rowId = str;
    }

    @Inject
    public GiftCardDetailUseCase(Repository repository) {
        this.mRepository = repository;
    }

    @Override // com.yltx.oil.partner.mvp.domain.UseCase
    protected Observable<HttpResult<GiftCardResp>> buildObservable() {
        return this.mRepository.getRechargeById(this.rowId);
    }
}