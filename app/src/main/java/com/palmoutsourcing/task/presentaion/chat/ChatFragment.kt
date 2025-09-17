package com.palmoutsourcing.task.presentaion.chat

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.palmoutsourcing.task.core.recyclerdecorators.VerticalSpaceDecorator
import com.palmoutsourcing.task.databinding.FragmentChatBinding

class ChatFragment : Fragment() {
    private var _binding: FragmentChatBinding? = null

    private val binding get() = _binding!!

    private val viewModel: ChatViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()

        viewModel.messages.observe(requireActivity()) {
            binding.recyclerView.adapter = MessagesAdapter(it)
        }
    }

    private fun initRecyclerView() {
        val spacingInPixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, requireContext().resources.displayMetrics).toInt()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.addItemDecoration(VerticalSpaceDecorator(space = spacingInPixels))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}