package com.lulee007.xitu.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lulee007.xitu.EntriesByTagActivity;
import com.lulee007.xitu.adapter.TagFollowAdapter;
import com.lulee007.xitu.models.Tag;
import com.lulee007.xitu.presenter.SubscribedTagsPresenter;
import com.lulee007.xitu.view.ITagFollowGuideView;

/**
 * User: lulee007@live.com
 * Date: 2015-12-19
 * Time: 21:46
 */
public class SubscribedTagsFragment extends BaseListFragment<Tag> implements ITagFollowGuideView, TagFollowAdapter.TagItemListener {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mListAdapter = new TagFollowAdapter();
        mListAdapter.setItemListener(this);
        ((TagFollowAdapter)mListAdapter).setTagItemListener(this);

        mUltimateRecyclerView.setAdapter(mListAdapter);
        mUltimateRecyclerView.addItemDividerDecoration(this.getContext());
        mUltimateRecyclerView.enableDefaultSwipeRefresh(false);

        mPresenter = new SubscribedTagsPresenter(this);
        mPresenter.loadNew();

        return view;
    }

    @Override
    public void showConfirm() {

    }

    @Override
    public void onUnSubscribeTag(int position) {
        ((TagFollowAdapter) mListAdapter).remove(position);
    }

    @Override
    public void onUnSubscribeTagError() {
        Toast.makeText(getContext(), "关注失败。", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(Object item) {
        Tag tag = (Tag) item;
        Intent intent = new Intent(getContext(), EntriesByTagActivity.class);
        intent.putExtra(EntriesByTagActivity.BUNDLE_KEY_TAG_TITLE,
                tag.getTitle());
        startActivity(intent);
    }

    @Override
    public void onFollowClick(Tag tag, int position) {
    }

    @Override
    public void onUnSubscribeClick(Tag tag, int position) {
        ((SubscribedTagsPresenter) mPresenter).unSubscribeTag(tag.getSubscribedId(), position);

    }
}