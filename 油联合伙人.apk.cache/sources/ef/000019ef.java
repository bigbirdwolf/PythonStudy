package com.yltx.oil.partner.modules.profit.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yltx.oil.partner.R;
import com.yltx.oil.partner.base.BaseFragment;
import com.yltx.oil.partner.base.Rx;
import com.yltx.oil.partner.modules.profit.adapter.Sy_Ddmx_ELV_Adapter;
import com.yltx.oil.partner.modules.profit.presenter.AllordersPresenter;
import com.yltx.oil.partner.modules.profit.response.AllordersResponse;
import com.yltx.oil.partner.mvp.views.PageLimitView;
import com.yltx.oil.partner.utils.ToastUtil;
import java.util.List;
import javax.inject.Inject;
import rx.functions.Action1;

/* loaded from: classes.dex */
public class AllordersFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener, PageLimitView<AllordersResponse>, BaseQuickAdapter.RequestLoadMoreListener {
    @Inject
    AllordersPresenter allordersPresenter;
    @BindView(R.id.ddmx_rv_list)
    RecyclerView ddmxRvList;
    @BindView(R.id.ddmx_srl_complain)
    SwipeRefreshLayout ddmxSrlComplain;
    Sy_Ddmx_ELV_Adapter mAdapter;
    @BindView(R.id.sy_ddmx_sydd_czk)
    RadioButton syDdmxSyddCzk;
    @BindView(R.id.sy_ddmx_sydd_czkk)
    RadioButton syDdmxSyddCzkk;
    @BindView(R.id.sy_ddmx_sydd_jyk)
    RadioButton syDdmxSyddJyk;
    @BindView(R.id.sy_ddmx_sydd_sp)
    RadioButton syDdmxSyddSp;
    @BindView(R.id.sy_ddmx_sydd_ypmy)
    RadioButton syDdmxSyddYpmy;
    Unbinder unbinder;
    private String YPMY = "1";
    private String JYK = "2";
    private String CZK = "3";
    private String SP = "0";
    private String CZKK = "5";
    private String Status = null;
    private String name = null;
    private String type = this.YPMY;

    @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
    }

    @Override // com.yltx.oil.partner.base.BaseFragment
    protected int setupContentView() {
        return R.layout.fragment_allorders;
    }

    public static AllordersFragment newInstance() {
        Bundle bundle = new Bundle();
        AllordersFragment allordersFragment = new AllordersFragment();
        allordersFragment.setArguments(bundle);
        return allordersFragment;
    }

    @Override // com.yltx.oil.partner.base.BaseFragment, android.support.v4.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        this.unbinder = ButterKnife.bind(this, onCreateView);
        return onCreateView;
    }

    @Override // com.yltx.oil.partner.base.BaseFragment, android.support.v4.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // android.support.v4.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.allordersPresenter.attachView(this);
        setupUI();
        bindListener();
    }

    protected void setupUI() {
        initRView();
        this.syDdmxSyddYpmy.setChecked(true);
        this.type = this.YPMY;
        this.allordersPresenter.setStatus(this.Status);
        this.allordersPresenter.setType(this.type);
        this.allordersPresenter.fetchFirst();
    }

    private void initRView() {
        this.mAdapter = new Sy_Ddmx_ELV_Adapter(null, null, "油品贸易");
        this.ddmxRvList.setLayoutManager(new LinearLayoutManager(getContext()));
        this.ddmxRvList.setAdapter(this.mAdapter);
        this.mAdapter.setOnItemClickListener(this);
        this.mAdapter.setOnLoadMoreListener(this, this.ddmxRvList);
    }

    @Override // com.yltx.oil.partner.base.BaseFragment, android.support.v4.app.Fragment
    public void onResume() {
        super.onResume();
        setupUI();
    }

    private void bindListener() {
        Rx.click(this.syDdmxSyddJyk, new Action1() { // from class: com.yltx.oil.partner.modules.profit.fragment.-$$Lambda$AllordersFragment$u-JZEpOkTDn67dK2AdVilvww42Y
            @Override // rx.functions.Action1
            public final void call(Object obj) {
                AllordersFragment.lambda$bindListener$0(AllordersFragment.this, (Void) obj);
            }
        });
        Rx.click(this.syDdmxSyddYpmy, new Action1() { // from class: com.yltx.oil.partner.modules.profit.fragment.-$$Lambda$AllordersFragment$veijgRY0maOat-sTKyhdFDCSdNQ
            @Override // rx.functions.Action1
            public final void call(Object obj) {
                AllordersFragment.lambda$bindListener$1(AllordersFragment.this, (Void) obj);
            }
        });
        Rx.click(this.syDdmxSyddCzk, new Action1() { // from class: com.yltx.oil.partner.modules.profit.fragment.-$$Lambda$AllordersFragment$7K55TGMbPSHapUEQbSTXqxAYQu4
            @Override // rx.functions.Action1
            public final void call(Object obj) {
                AllordersFragment.lambda$bindListener$2(AllordersFragment.this, (Void) obj);
            }
        });
        Rx.click(this.syDdmxSyddSp, new Action1() { // from class: com.yltx.oil.partner.modules.profit.fragment.-$$Lambda$AllordersFragment$uOcjRISRBsRrjr0fLsP4SXcFpG8
            @Override // rx.functions.Action1
            public final void call(Object obj) {
                AllordersFragment.lambda$bindListener$3(AllordersFragment.this, (Void) obj);
            }
        });
        Rx.click(this.syDdmxSyddCzkk, new Action1() { // from class: com.yltx.oil.partner.modules.profit.fragment.-$$Lambda$AllordersFragment$9koDOfvdXA0gDcBNUoZUDLV9S4s
            @Override // rx.functions.Action1
            public final void call(Object obj) {
                AllordersFragment.lambda$bindListener$4(AllordersFragment.this, (Void) obj);
            }
        });
        this.ddmxSrlComplain.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: com.yltx.oil.partner.modules.profit.fragment.-$$Lambda$AllordersFragment$SkfTCntqUhryidgN6ddrvKBQUOM
            @Override // android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener
            public final void onRefresh() {
                AllordersFragment.lambda$bindListener$5(AllordersFragment.this);
            }
        });
    }

    public static /* synthetic */ void lambda$bindListener$0(AllordersFragment allordersFragment, Void r3) {
        allordersFragment.mAdapter = new Sy_Ddmx_ELV_Adapter(null, null, "加油卡");
        allordersFragment.ddmxRvList.setLayoutManager(new LinearLayoutManager(allordersFragment.getContext()));
        allordersFragment.ddmxRvList.setAdapter(allordersFragment.mAdapter);
        allordersFragment.mAdapter.setOnItemClickListener(allordersFragment);
        allordersFragment.mAdapter.setOnLoadMoreListener(allordersFragment, allordersFragment.ddmxRvList);
        allordersFragment.type = allordersFragment.JYK;
        allordersFragment.getComplain();
    }

    public static /* synthetic */ void lambda$bindListener$1(AllordersFragment allordersFragment, Void r3) {
        allordersFragment.mAdapter = new Sy_Ddmx_ELV_Adapter(null, null, "油品贸易");
        allordersFragment.ddmxRvList.setLayoutManager(new LinearLayoutManager(allordersFragment.getContext()));
        allordersFragment.ddmxRvList.setAdapter(allordersFragment.mAdapter);
        allordersFragment.mAdapter.setOnItemClickListener(allordersFragment);
        allordersFragment.mAdapter.setOnLoadMoreListener(allordersFragment, allordersFragment.ddmxRvList);
        allordersFragment.type = allordersFragment.YPMY;
        allordersFragment.getComplain();
    }

    public static /* synthetic */ void lambda$bindListener$2(AllordersFragment allordersFragment, Void r3) {
        allordersFragment.mAdapter = new Sy_Ddmx_ELV_Adapter(null, null, "储值卡");
        allordersFragment.ddmxRvList.setLayoutManager(new LinearLayoutManager(allordersFragment.getContext()));
        allordersFragment.ddmxRvList.setAdapter(allordersFragment.mAdapter);
        allordersFragment.mAdapter.setOnItemClickListener(allordersFragment);
        allordersFragment.mAdapter.setOnLoadMoreListener(allordersFragment, allordersFragment.ddmxRvList);
        allordersFragment.type = allordersFragment.CZK;
        allordersFragment.getComplain();
    }

    public static /* synthetic */ void lambda$bindListener$3(AllordersFragment allordersFragment, Void r4) {
        allordersFragment.mAdapter = new Sy_Ddmx_ELV_Adapter(null, "商品", "商品");
        allordersFragment.ddmxRvList.setLayoutManager(new LinearLayoutManager(allordersFragment.getContext()));
        allordersFragment.ddmxRvList.setAdapter(allordersFragment.mAdapter);
        allordersFragment.mAdapter.setOnItemClickListener(allordersFragment);
        allordersFragment.mAdapter.setOnLoadMoreListener(allordersFragment, allordersFragment.ddmxRvList);
        allordersFragment.type = allordersFragment.SP;
        allordersFragment.getComplain();
    }

    public static /* synthetic */ void lambda$bindListener$4(AllordersFragment allordersFragment, Void r3) {
        allordersFragment.mAdapter = new Sy_Ddmx_ELV_Adapter(null, null, "礼品卡");
        allordersFragment.ddmxRvList.setLayoutManager(new LinearLayoutManager(allordersFragment.getContext()));
        allordersFragment.ddmxRvList.setAdapter(allordersFragment.mAdapter);
        allordersFragment.mAdapter.setOnItemClickListener(allordersFragment);
        allordersFragment.mAdapter.setOnLoadMoreListener(allordersFragment, allordersFragment.ddmxRvList);
        allordersFragment.type = allordersFragment.CZKK;
        allordersFragment.getComplain();
    }

    public static /* synthetic */ void lambda$bindListener$5(AllordersFragment allordersFragment) {
        allordersFragment.allordersPresenter.fetchTop();
    }

    private void getComplain() {
        this.Status = null;
        this.allordersPresenter.setStatus(this.Status);
        this.allordersPresenter.setType(this.type);
        this.allordersPresenter.fetchFirst();
    }

    @Override // com.yltx.oil.partner.base.BaseFragment, android.support.v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }

    private void setData(AllordersResponse allordersResponse) {
        if (allordersResponse == null || allordersResponse.getData().size() == 0) {
            this.mAdapter.loadMoreEnd();
        } else if (allordersResponse.getData().size() < 10) {
            this.mAdapter.setEnableLoadMore(false);
            this.mAdapter.loadMoreEnd();
        } else {
            this.mAdapter.setEnableLoadMore(true);
            this.mAdapter.loadMoreComplete();
        }
        this.mAdapter.setNewData(allordersResponse.getData());
        this.mAdapter.disableLoadMoreIfNotFullPage();
    }

    private void setMoreData(AllordersResponse allordersResponse) {
        if (allordersResponse.getData().size() < 10) {
            this.mAdapter.setEnableLoadMore(false);
            this.mAdapter.loadMoreEnd();
        } else {
            this.mAdapter.setEnableLoadMore(true);
            this.mAdapter.loadMoreComplete();
        }
        this.mAdapter.addData((List) allordersResponse.getData());
    }

    @Override // com.yltx.oil.partner.mvp.views.PageLimitView
    public void render(AllordersResponse allordersResponse) {
        setData(allordersResponse);
    }

    @Override // com.yltx.oil.partner.mvp.views.PageLimitView
    public void onRefreshComplete(AllordersResponse allordersResponse) {
        setData(allordersResponse);
        this.ddmxSrlComplain.setRefreshing(false);
    }

    @Override // com.yltx.oil.partner.mvp.views.PageLimitView
    public void onLoadMoreComplete(AllordersResponse allordersResponse) {
        this.ddmxSrlComplain.setRefreshing(false);
        setMoreData(allordersResponse);
    }

    @Override // com.yltx.oil.partner.mvp.views.PageLimitView
    public void onRefreshError(String str) {
        this.ddmxSrlComplain.setRefreshing(false);
        ToastUtil.showMiddleScreenToast(str);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter.RequestLoadMoreListener
    public void onLoadMoreRequested() {
        this.allordersPresenter.fetchMore();
    }

    @Override // com.yltx.oil.partner.mvp.views.PageLimitView
    public void onLoadMoreError(String str) {
        this.mAdapter.loadMoreFail();
    }
}